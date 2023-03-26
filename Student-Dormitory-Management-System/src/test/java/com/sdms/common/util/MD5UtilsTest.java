package com.sdms.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MD5UtilsTest {

    @Test
    void encode() {
        assertEquals("202cb962ac59075b964b07152d234b70", MD5Utils.encode("123"));
    }

    @Test
    void encodeWithSalt() {
        assertEquals("0192023a7bbd73250516f069df18b500", MD5Utils.encodeWithSalt("123", "admin", 1));
    }
}