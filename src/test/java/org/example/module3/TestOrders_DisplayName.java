package org.example.module3;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrders_DisplayName {


    @Test
    @Order(1)
    @DisplayName("first test")
    void testOrder() {
        assertThat("hello").isEqualTo("hello");
    }

    @Test
    @Order(2)
    @DisplayName("second test")
    void s2s() {
        assertThat("hello").isEqualTo("hello");
    }
}
