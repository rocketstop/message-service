package com.mostlysafe.message;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan
//@Import(MessageServiceConfiguration.class)
public class MessageServiceApplication {

	protected Logger logger = Logger.getLogger(MessageServiceApplication.class.getName());

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "message-server");
		SpringApplication.run(MessageServiceApplication.class, args);
	}

}
