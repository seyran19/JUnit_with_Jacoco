package org.example.module3;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class NestedClass {

    @Nested
    class LoginTest{

        @DisplayName("test1")
        @Test
        void test1(){
            assert true;
        }

        @DisplayName("test2")
        @Test
        void test2(){
            assert false;
        }
    }
}
