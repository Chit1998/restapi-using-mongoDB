package com.cds.testmongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.cds.testmongodb"})
public class TestMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestMongoDbApplication.class, args);
	}

}
