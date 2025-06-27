package com.backend.irire;

//import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IrireApplication {


    public static void main(String[] args) throws Exception{
//        Dotenv dotenv = Dotenv.configure()
//                .directory("./.env")
//                .ignoreIfMissing() // Don't fail if .env is missing
//                .load();
//
//        // Set environment variables as system properties for Spring to use
//        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(IrireApplication.class, args);
    }

}
