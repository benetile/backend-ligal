package com.backoffice;

import com.backoffice.services.adresse.ProvinceService;
import com.backoffice.services.adresse.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class BackofficeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackofficeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//provinceService.initProvinces();
		//villeService.initVilles();
	}
}
