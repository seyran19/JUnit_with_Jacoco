package org.example.service;

import org.example.dao.UserDao;
import org.example.dto.User;
import org.example.paramresolver.UserServiceParamResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// означает что мы создаем 1 объект UserServiceTest
// для всех тестов и теперь методы BeforeAll и AfterAll не обязательно
// делать статическими.
// по умолчанию @TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("fast") // можем теперь запустить тесты с определенным тегом
// порядок запуска тестов задаем
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// для инжектинга
@ExtendWith({
        UserServiceParamResolver.class
})
// указали таймаут для всех тестов
@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
public class UserServiceTest {

    private UserService userService;
    private UserDao userDao;
    private static final User IVAN = User.of(1, "Ivan", 123);
    private static final User ALICE = User.of(2, "Alice", 456);

    UserServiceTest(TestInfo testInfo) {
        System.out.println("Предоставляем информацию о тестовом классе");
    }

    @BeforeAll
    void init() {
        System.out.println("Before All " + this);
    }

    // Закоментирую так как я не хчоу чтобы userService
    // внедрялся автоматически
//    @BeforeEach
//    void prepare(UserService userService) {
//        System.out.println("Before each test " + this);
//        this.userService = userService;
//
//    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each test " + this);
        this.userDao = Mockito.mock(UserDao.class);
        this.userService = new UserService(userDao);
        // можно над моком использовать аннотацию мок а над классом куда
        // мок будет инджектиться @InjectMock

    }

    @Test
    // Порядок запуска теста
    @Order(1)
    // отображение при выводе (как он будет называться при выводе)
    @DisplayName("Users will be empty if no users added")
    void usersEmptyIfNoUserAdded() {
        System.out.println("Test1 " + this);
        var users = userService.getAllUsers();
        assertTrue(users.isEmpty(), () -> "Users should be empty");
    }

    @Test
    void usersSizeIfUsersAdded() {
        System.out.println("Test2 " + this);
        userService.add(IVAN);
        userService.add(ALICE);

        var users = userService.getAllUsers();

//        assertEquals(2, users.size(), () -> "expected other count of users");
        assertThat(users).hasSize(2);
    }


    @Test
    void loginFailIfPasswordIncorrect() {
        userService.add(IVAN);
        Optional<User> user = userService.login(IVAN.getName(), 12);
        assertFalse(user.isPresent(), () -> "user should be null");
    }

    @Test
    void loginFailIfUserDoesNotExist() {
        Optional<User> user = userService.login(IVAN.getName(), 12);
        assertTrue(user.isEmpty(), () -> "user should be null");
    }

    @Test
    void usersConvertedToMapById() {
        userService.add(IVAN);
        userService.add(ALICE);
        Map<Integer, User> users = userService.getAllConvertedById();

        assertAll(
                () -> assertThat(users).containsKeys(IVAN.getId(), ALICE.getId()),
                () -> assertThat(users).containsValues(IVAN, ALICE)
        );

    }

    @Test
    void shouldDeleteExcisedUser(){
        userService.add(IVAN);
        // мы создали stub объект
//        Mockito.doReturn(true).when(userDao).delete(IVAN.getId());
        // dummy объект
        Mockito.doReturn(true).when(userDao).delete(Mockito.any(Integer.class));
        // то же самое
//        Mockito.when(userDao.delete(Mockito.any(Integer.class))).thenReturn(true);
        boolean result = userService.deleteById(IVAN.getId());
        assertThat(result).isTrue();

//        Mockito.verify(userDao, Mockito.times(3)).delete(Mockito.any(Integer.class));
        // перехватываем аргументы
        // Можно создать с помощью аннотации Captor
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        // был-ли 1 раз вызван метод delete
        Mockito.verify(userDao).delete(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(IVAN.getId());

    }

    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("After each test " + this);
    }

    @AfterAll
    void destroy() {
        System.out.println("After All " + this);
    }

    // чтобы красивое разделение было перенесли тесты которые относятся к логину в отдельный класс
    @Nested
    @DisplayName("Test user login functionality")
    @Tag("login")
    class LoginTest {

//        @Test
        // тест повторится 3 раза
        @RepeatedTest(3)
        void throwExceptionIfUsernameIsNull() {
            var exc = assertThrows(IllegalArgumentException.class,
                    () -> userService.login(null, 12));
        }

        @Test
        // Заигнорили тест
        @Disabled("Disabled for test")
        void loginSuccessIfUserExists() {
            userService.add(IVAN);
            Optional<User> user = userService.login(IVAN.getName(), IVAN.getPassword());

            assertThat(user).isPresent();
//        assertTrue(user.isPresent(), () -> "user should not be null");
            assertEquals(user.get(), IVAN);
        }

        @ParameterizedTest
        // если метод принимает только 1 аргумент используется!
        // В нашем случае метод будет вызван сначала с параметром chuck
        // а потом puck
//        @ValueSource(strings = {"chuck", "puck"})
        // Указываем ися метода который вернет аргументы
        @MethodSource("org.example.service.UserServiceTest#getArgsForLoginTest")
        void loginParameterizedTest(String username,  int password, Optional<User> user) {
            userService.add(IVAN);
            userService.add(ALICE);
            Optional<User> maybeUser = userService.login(username, password);
            assertThat(maybeUser).isEqualTo(user);
        }

        @Test
        void checkLoginFunctionalityPerformance(){
            assertTimeout(Duration.ofMillis(200L), () -> {
//                Thread.sleep(300);
                userService.login(IVAN.getName(), IVAN.getPassword());
            });

        }

    }

    static Stream<Arguments> getArgsForLoginTest(){
        return Stream.of(
                Arguments.of("Ivan", 123, Optional.of(IVAN)),
                Arguments.of("Alice", 456, Optional.of(ALICE))

        );
    }
}
