package com.example.hexhive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HexhiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexhiveApplication.class, args);
	}

	@Bean
	CommandLineRunner initData() {
		return args -> {
			System.out.println("✅ Hexhive IoT Dashboard initialized!");
			System.out.println("🌐 Open http://localhost/ to view the control panel");
			System.out.println("🗄️  Open http://localhost/h2-console for database access");
		};
	}
}
