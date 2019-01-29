package com.uxpsystems.assignement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AssignementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignementApplication.class, args);
	}

//	@Bean
//	HazelcastInstance hazelcastInstance() {
//		ClientConfig config = new ClientConfig();
//		config.getGroupConfig().setName("dev").setPassword("dev-pass");
//		config.getNetworkConfig().addAddress("192.168.99.100");
//		config.setInstanceName("cache-1");
//		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
//		return instance;
//	}
//
//	@Bean
//	CacheManager cacheManager() {
//		return new HazelcastCacheManager(hazelcastInstance());
//	}

}
