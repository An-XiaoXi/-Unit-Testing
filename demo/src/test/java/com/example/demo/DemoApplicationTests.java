package com.example.demo;

import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    /* Dao 层接口 */
    @Autowired
    private AdminMapper adminMapper;

    @Test
    void contextLoads() {

        List<Admin> admins = adminMapper.FindAll();

        System.out.println(admins);
    }

}
