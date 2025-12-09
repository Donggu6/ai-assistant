package com.deongeon.ai.assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.deongeon.ai")
public class AiAssistantApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AiAssistantApplication.class, args);
	}

}
