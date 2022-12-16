package umc.CarrotMarket_Clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarrotMarketCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrotMarketCloneApplication.class, args);
	}

}
