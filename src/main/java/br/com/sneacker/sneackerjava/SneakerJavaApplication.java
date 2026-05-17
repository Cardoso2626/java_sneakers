package br.com.sneacker.sneackerjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SneakerJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SneakerJavaApplication.class, args);
    }

}
