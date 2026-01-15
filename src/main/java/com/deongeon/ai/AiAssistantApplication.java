package com.deongeon.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiAssistantApplication {

    public static void main(String[] args) {

        System.out.println("OPENAI_KEY = " + System.getenv("OPENAI_KEY"));

        SpringApplication.run(AiAssistantApplication.class, args);
    }
}
