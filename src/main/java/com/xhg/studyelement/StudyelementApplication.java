package com.xhg.studyelement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 16033
 */
@SpringBootApplication(scanBasePackages = {"com.xhg.studyelement"})
public class StudyelementApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudyelementApplication.class, args);
	}
}
