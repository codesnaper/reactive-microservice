package org.example.product.productApi;

import org.basicAuth.security.Repository.IUserRespository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"org.example","org.basicAuth"})
public class Application {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.getBean(IUserRespository.class).addDefaultUser();

    }
}
