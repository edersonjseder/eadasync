package com.ead.configserver;

import com.ead.configserver.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@RequiredArgsConstructor
public class ConfigServerApplication implements CommandLineRunner {
	private final UserUtils userUtils;
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		userUtils.createAdminUser();
	}
}
