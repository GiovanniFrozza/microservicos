package io.github.giovannifrozza.msclietes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsclietesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsclietesApplication.class, args);
	}

}
