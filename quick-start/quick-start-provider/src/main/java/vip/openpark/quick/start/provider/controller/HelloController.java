package vip.openpark.quick.start.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vip.openpark.resilience4j.api.IHelloService;

import java.util.concurrent.TimeUnit;

/**
 * @author anthony
 * @version 2024/9/28
 * @since 2024/9/28 22:02
 */
@Slf4j
@RestController
public class HelloController implements IHelloService {
	@Override
	@GetMapping("hello/{id}")
	public String hello(@PathVariable(name = "id", required = false) Long id) {
		if (id < 0) {
			throw new RuntimeException("id < 0");
		}

		if (id > 100) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				log.info("sleep 5 seconds", e);
			}
		}

		return "Hello resilience4j. The id is " + id;
	}
}
