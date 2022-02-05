package com.juansecu.opentoonix;

/* --- Third-party modules --- */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OpenToonixApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenToonixApplication.class, args);
	}

}
