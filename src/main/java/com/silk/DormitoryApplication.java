package com.silk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LindaSilk
 * @date
 */
@SpringBootApplication
@MapperScan({"com.silk.mapper"})
public class DormitoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormitoryApplication.class, args);
    }

}
