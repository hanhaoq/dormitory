package com.sdms.common.util;

import com.sdms.entity.MainTenance;
import com.sdms.entity.Student;
import com.sdms.entity.User;
import com.sdms.service.MainTenanceService;
import com.sdms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
@SpringBootTest
class Test {

    @org.junit.jupiter.api.Test
    void nullCast() {
        User user = (User) null;
        System.out.println("user = " + user);
    }

    @Autowired
    private MainTenanceService mainTenanceService;
    @org.junit.jupiter.api.Test
    void context(){
        List<MainTenance.status> statuses = mainTenanceService.listAllStatuses();
        System.out.println(Arrays.toString(statuses.toArray()));
    }
    @Autowired
    private StudentService studentService;
    @org.junit.jupiter.api.Test
    void add(){
        Student studentByUserId = studentService.getStudentByUserId("9");
        System.out.println(studentByUserId.getId()+""+studentByUserId.getRoomId());

        //stuId:01219  roomId 2 description
//        mainTenanceService.newMainTenance("01219",2L,"门坏了");

    }
}