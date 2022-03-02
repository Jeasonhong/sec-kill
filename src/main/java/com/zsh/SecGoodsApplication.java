package com.zsh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.zsh.dao.mapper")
public class SecGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecGoodsApplication.class, args);
    }

}
