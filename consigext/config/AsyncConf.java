package br.mil.fab.consigext.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConf {
	
	  @Bean(name = "fileExecutor")
	  public Executor getAsyncExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		    executor.setCorePoolSize(10);
		    executor.setMaxPoolSize(10);
		    executor.setQueueCapacity(20);
		    executor.setThreadNamePrefix("AsyncUpdateBatch-");
		    executor.initialize();
		    return executor;		
		}
		


}
