package com.aluracursos.ydwatch;

import com.aluracursos.ydwatch.model.DatosEpisodio;
import com.aluracursos.ydwatch.model.DatosSerie;
import com.aluracursos.ydwatch.model.DatosTemporadas;
import com.aluracursos.ydwatch.principal.Principal;
import com.aluracursos.ydwatch.repositorio.SerieRepository;
import com.aluracursos.ydwatch.service.ConsumoAPI;
import com.aluracursos.ydwatch.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class YdwatchApplication  {
	public static void main(String[] args) {
		SpringApplication.run(YdwatchApplication.class, args);
	}

}
