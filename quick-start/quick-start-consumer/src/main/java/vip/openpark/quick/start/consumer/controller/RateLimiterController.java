package vip.openpark.quick.start.consumer.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vip.openpark.resilience4j.api.IHelloService;

/**
 * @author anthony
 * @version 2024/9/29
 * @since 2024/9/29 15:45
 */
@Slf4j
@RestController
public class RateLimiterController {
	@Resource
	private IHelloService helloService;

	@GetMapping("hello4RateLimiter/{id}")
	@RateLimiter(name = "quick-start-provider", fallbackMethod = "fallback4RateLimiter")
	public String hello4RateLimiter(@PathVariable(name = "id", required = false) Long id) {
		return helloService.hello(id);
	}

	/**
	 * 限流后的处理方法
	 *
	 * @param id Long
	 * @param e  Throwable
	 * @return String
	 */
	private String fallback4RateLimiter(Long id, Throwable e) {
		log.error("限流请求参数：{}，异常信息:{}", id, e.getMessage());
		return "hello fallback4RateLimiter";
	}
}