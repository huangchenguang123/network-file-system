package com.chuxing.nfs.server.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @date 2022/5/25 13:53
 * @author huangchenguang
 * @desc starter
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.chuxing.nfs")
public class NfsServerApplication {

    /**
     * @date 2022/6/27 15:23
     * @author huangchenguang
     * @desc main
     */
    public static void main(String[] args) {
        SpringApplication.run(NfsServerApplication.class, args);
    }

}
