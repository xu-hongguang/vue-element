package com.xhg.studyelement;

import com.xhg.studyelement.common.config.propertiesconfig.FebsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 运行
 *
 * @author 16033
 */
@SpringBootApplication
@EnableConfigurationProperties({FebsProperties.class})
@EnableCaching
public class StudyelementApplication {

	private static Logger log = LoggerFactory.getLogger(StudyelementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StudyelementApplication.class, args);
		log.info("《《《《《《 MySystem started up successfully at {} {} 》》》》》》", LocalDate.now(), LocalTime.now());
	}
}
