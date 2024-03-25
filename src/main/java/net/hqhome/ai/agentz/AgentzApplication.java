package net.hqhome.ai.agentz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("net.hqhome.ai.agentz.infrastructor")
public class AgentzApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentzApplication.class, args);
	}

}
