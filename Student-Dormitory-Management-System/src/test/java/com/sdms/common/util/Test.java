package com.sdms.common.util;

import com.sdms.entity.User;

class Test {

    @org.junit.jupiter.api.Test
    void nullCast() {
        User user = (User) null;
        System.out.println("user = " + user);
    }
}