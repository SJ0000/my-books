package sungjin.mybooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MybooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybooksApplication.class, args);
	}

}
