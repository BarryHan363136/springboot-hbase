package com.barry.bigdata.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 描述: 系统启动主方法
 * @SpringBootApplication等同于 @Configuration @EnableAutoConfiguration @ComponentScanpublic
 *
 * @outhor hants
 * @create 2018-05-01 下午5:29
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.barry.bigdata"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class HbaseServiceStarter {

    public static void main(String[] args) {
        SpringApplication.run(HbaseServiceStarter.class, args);
    }

}