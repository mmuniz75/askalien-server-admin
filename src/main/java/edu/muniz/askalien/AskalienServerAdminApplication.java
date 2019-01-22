package edu.muniz.askalien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"edu.muniz.askalien.client"})
public class AskalienServerAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AskalienServerAdminApplication.class, args);
	}
}
