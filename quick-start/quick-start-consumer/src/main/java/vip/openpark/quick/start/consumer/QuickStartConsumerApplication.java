package vip.openpark.quick.start.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author anthony
 * @version 2024/9/28
 * @since 2024/9/28 22:09
 */
@EnableFeignClients(basePackages = "vip.openpark.resilience4j.api")
@SpringBootApplication
public class QuickStartConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuickStartConsumerApplication.class, args);
	}
}