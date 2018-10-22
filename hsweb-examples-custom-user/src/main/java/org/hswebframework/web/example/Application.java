package org.hswebframework.web.example;

import org.hswebframework.web.authorization.basic.configuration.EnableAopAuthorize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhouhao
 */
@SpringBootApplication
@EnableAopAuthorize
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
