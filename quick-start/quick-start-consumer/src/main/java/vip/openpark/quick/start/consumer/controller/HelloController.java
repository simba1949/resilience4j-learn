package vip.openpark.quick.start.consumer.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vip.openpark.resilience4j.api.IHelloService;

import java.util.concurrent.CompletableFuture;

/**
 * @author anthony
 * @version 2024/9/28
 * @since 2024/9/28 22:12
 */
@Slf4j
@RestController
public class HelloController {
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
	@GetMapping("circuitbreakerHello/{id}")
	@CircuitBreaker(name = "quick-start-provider", fallbackMethod = "helloFallback")
	public String circuitbreakerHello(@PathVariable(name = "id", required = false) Long id) {
		return helloService.hello(id);
	}

	/**
	 * 熔断后的处理方法
	 *
	 * @param id Long
	 * @param e  Throwable
	 * @return 熔断后的返回值
	 */
	private String circuitbreakerHelloFallback(Long id, Throwable e) {
		log.error("熔断请求参数：{}，异常信息:{}", id, e.getMessage());
		return "hello circuitbreakerHello";
	}

	/**
	 * 隔舱测试
	 * 使用了信号量
	 *
	 * @param id Long
	 * @return String
	 */
	@GetMapping("bulkheadHello4Semaphore/{id}")
	@Bulkhead(name = "quick-start-provider", fallbackMethod = "bulkheadHello4SemaphoreFallback", type = Bulkhead.Type.SEMAPHORE)
	public String bulkheadHello4Semaphore(@PathVariable(name = "id", required = false) Long id) {
		return helloService.hello(id);
	}

	/**
	 * 隔舱后的处理方法
	 *
	 * @param id Long
	 * @param e  Throwable
	 * @return String
	 */
	private String bulkheadHello4SemaphoreFallback(Long id, Throwable e) {
		log.error("隔舱请求参数：{}，异常信息:{}", id, e.getMessage());
		return "hello bulkheadHelloFallback";
	}

	/**
	 * 隔舱测试
	 * 使用了有界队列和固定大小线程池
	 *
	 * @param id Long
	 * @return CompletableFuture<String> 必须使用 CompletableFuture
	 */
	@GetMapping("bulkheadHello4ThreadPool/{id}")
	@Bulkhead(name = "quick-start-provider", fallbackMethod = "bulkheadHello4ThreadPoolFallback", type = Bulkhead.Type.THREADPOOL)
	public CompletableFuture<String> bulkheadHello4ThreadPool(@PathVariable(name = "id", required = false) Long id) {
		return CompletableFuture.supplyAsync(() -> helloService.hello(id));
	}

	/**
	 * 隔舱后的处理方法
	 *
	 * @param id Long
	 * @param e  Throwable
	 * @return String
	 */
	private String bulkheadHello4ThreadPoolFallback(Long id, Throwable e) {
		log.error("隔舱请求参数：{}，异常信息:{}", id, e.getMessage());
		return "hello bulkheadHelloFallback";
	}
}