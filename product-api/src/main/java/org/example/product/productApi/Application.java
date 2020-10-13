package org.example.product.productApi;

import org.security.Repository.IUserRespository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"org.example","org.security","org.utility"})
public class Application {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        try{
            ctx.getBean(IUserRespository.class).addDefaultUser();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
