package com.apiMedicMax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apiMedicMax.models.Rol;
import com.apiMedicMax.repositories.RolRepository;

@SpringBootApplication(scanBasePackages = "com.apiMedicMax")
public class ApiMedicMaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMedicMaxApplication.class, args);
	}

	@Bean
	public org.springframework.boot.CommandLineRunner seedRoles(RolRepository rolRepository) {
		return args -> {
			if (!rolRepository.existsByNombre("ROLE_USER")) {
				Rol userRole = new Rol();
				userRole.setNombre("ROLE_USER");
				rolRepository.save(userRole);
			}
			if (!rolRepository.existsByNombre("ROLE_ADMIN")) {
				Rol adminRole = new Rol();
				adminRole.setNombre("ROLE_ADMIN");
				rolRepository.save(adminRole);
			}
		};
	}

}
