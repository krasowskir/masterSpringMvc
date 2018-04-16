package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.config.PicturesUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({ PicturesUploadProperties.class })
public class DemoApplication {

    public static void main(String[] args) {
	SpringApplication.run(DemoApplication.class, args);
    }
}
