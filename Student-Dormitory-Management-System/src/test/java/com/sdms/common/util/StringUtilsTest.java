package com.sdms.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    void isNumeric() {
        assertTrue(StringUtils.isNumeric("01234"));
        assertTrue(StringUtils.isNumeric("999"));
        assertFalse(StringUtils.isNumeric("999 "));
        assertFalse(StringUtils.isNumeric("abc"));
        assertFalse(StringUtils.isNumeric("abc"));
        assertFalse(StringUtils.isNumeric(" abc"));
        assertFalse(StringUtils.isNumeric("a  b    c"));
    }

    @Test
    void isBlankOrNull() {
        assertTrue(StringUtils.isBlankOrNull(null));
        assertTrue(StringUtils.isBlankOrNull(""));
        assertTrue(StringUtils.isBlankOrNull("    "));
        assertTrue(StringUtils.isBlankOrNull("    \t \r \n"));
        assertFalse(StringUtils.isBlankOrNull("   aaa \t \r \n"));
    }

    @Test
    void parseLongArray() {
        System.out.println(StringUtils.parseLongList(null));
        System.out.println(StringUtils.parseLongList(""));
        System.out.println(StringUtils.parseLongList("    "));
        System.out.println(StringUtils.parseLongList("  a,nb,"));
        System.out.println(StringUtils.parseLongList("12,34,564,a"));
        System.out.println(StringUtils.parseLongList("1,2,3,4"));
        System.out.println(StringUtils.parseLongList("1,2,3,4,"));
        System.out.println(StringUtils.parseLongList("  1  ,  ,3,4,  7  "));
        System.out.println(StringUtils.parseLongList("1"));
    }
}