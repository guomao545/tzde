package com.wonders.tzde;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement//启动事务
@MapperScan("com.wonders.tzde.mapper")
public class TzdeApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TzdeApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TzdeApplication.class, args);
    }
}
