package vip.openpark.resilience4j.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author anthony
 * @version 2024/9/28
 * @since 2024/9/28 21:59
 */
@FeignClient(name = "quick-start-provider")
public interface IHelloService {

	@GetMapping("hello/{id}")
	String hello(@PathVariable(name = "id", required = false) Long id);
}