package net.assessment.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableAsync
public class SpringbootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

}
