package com.ead.serviceregistry;

import com.ead.serviceregistry.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@RequiredArgsConstructor
public class ServiceRegistryApplication implements CommandLineRunner {
	private final UserUtils userUtils;
	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryApplication.class, args);
	}

	@Override
	public void run(String... args) {
		userUtils.createAdminUser();
	}
}
