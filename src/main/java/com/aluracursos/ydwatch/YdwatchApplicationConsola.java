package com.aluracursos.ydwatch;

import com.aluracursos.ydwatch.principal.Principal;
import com.aluracursos.ydwatch.repositorio.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YdwatchApplicationConsola implements CommandLineRunner {
	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(YdwatchApplicationConsola.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println("Hola mundo desde Spring");
		Principal principal = new Principal(repository);
		principal.muestraElMenu();
	}
}
