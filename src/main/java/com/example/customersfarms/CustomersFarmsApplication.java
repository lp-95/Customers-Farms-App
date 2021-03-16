package com.example.customersfarms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CustomersFarmsApplication {

	public static void main( String[] args ) {
		SpringApplication.run( CustomersFarmsApplication.class, args );
	}

}
