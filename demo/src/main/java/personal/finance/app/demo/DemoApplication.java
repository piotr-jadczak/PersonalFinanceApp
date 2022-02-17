package personal.finance.app.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import personal.finance.app.demo.domain.entity.Role;
import personal.finance.app.demo.repository.RoleRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Autowired
	public CommandLineRunner setupApp(RoleRepository roleRepository) {
		return args -> {
			roleRepository.save(new Role("user"));
		};
	}

}
