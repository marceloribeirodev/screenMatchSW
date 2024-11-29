package com.marceloribeirodev.screenmatch;

import com.marceloribeirodev.screenmatch.principal.NewPrincipal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		NewPrincipal newPrincipal = new NewPrincipal();
		newPrincipal.exibeMenu();
	}
}
