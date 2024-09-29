package vip.openpark.quick.start.consumer.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vip.openpark.resilience4j.api.IHelloService;

/**
 * @author anthony
 * @version 2024/9/28
 * @since 2024/9/28 22:12
 */
@Slf4j
@RestController
public class CircuitBreakerHelloController {
	@Resource
	private IHelloService helloService;

	/**
	 * 测试熔断
	 * 注解 @CircuitBreaker(name = "#root.methodName", fallbackMethod = "helloFallback")
	 * name = "#root.methodName" 表示当前方法被熔断时的名称
	 * 启动 fallbackMethod = "helloFallback" 表示熔断后的处理方法
	 *
	 * @param id Long
	 * @return String
	 */
	@GetMapping("hello4CircuitBreaker/{id}")
	@CircuitBreaker(name = "quick-start-provider", fallbackMethod = "fallback4CircuitBreaker")
	public String hello4CircuitBreaker(@PathVariable(name = "id", required = false) Long id) {
		return helloService.hello(id);
	}

	/**
	 * 熔断后的处理方法
	 *
	 * @param id Long
	 * @param e  Throwable
	 * @return 熔断后的返回值
	 */
	private String fallback4CircuitBreaker(Long id, Throwable e) {
		log.error("熔断请求参数：{}，异常信息:{}", id, e.getMessage());
		return "hello circuitbreakerHello";
	}
}