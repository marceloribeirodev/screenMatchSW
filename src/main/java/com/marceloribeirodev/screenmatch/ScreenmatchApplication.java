package com.marceloribeirodev.screenmatch;

import com.marceloribeirodev.screenmatch.model.DadosEpisodios;
import com.marceloribeirodev.screenmatch.model.DadosSerie;
import com.marceloribeirodev.screenmatch.model.DadosTemporada;
import com.marceloribeirodev.screenmatch.service.ConsumoApi;
import com.marceloribeirodev.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);

		var jsonCoffee = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(jsonCoffee);

		ConverteDados converteDados = new ConverteDados();

		DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=6585022c");
		DadosEpisodios dadosEpisodios = converteDados.obterDados(json, DadosEpisodios.class);
		System.out.println(dadosEpisodios);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++){
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season="+ i + "&apikey=6585022c");
			DadosTemporada dadosTemporada = converteDados.obterDados(json,DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);
	}
}
