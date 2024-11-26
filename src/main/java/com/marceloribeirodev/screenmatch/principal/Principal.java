package com.marceloribeirodev.screenmatch.principal;

import com.marceloribeirodev.screenmatch.model.DadosEpisodios;
import com.marceloribeirodev.screenmatch.model.DadosSerie;
import com.marceloribeirodev.screenmatch.model.DadosTemporada;
import com.marceloribeirodev.screenmatch.model.Episodio;
import com.marceloribeirodev.screenmatch.service.ConsumoApi;
import com.marceloribeirodev.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i<=dados.totalTemporadas(); i++){
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season="+ i + API_KEY);
            DadosTemporada dadosTemporada = converteDados.obterDados(json,DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

        for (int i = 0; i < dados.totalTemporadas(); i++){
            List<DadosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
            for( int j = 0;j < episodiosTemporada.size(); j++){
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }

//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//        temporadas.forEach(System.out::println);

//        List<String> nomes = Arrays.asList("Jacque","Iasmin", "Paulo", "Rodrigo", "Nico");
//
//        nomes.stream().sorted().limit(3).filter(n -> n.startsWith("N")).map(n -> n.toUpperCase())
//                .forEach(System.out::println);

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println(dadosEpisodios);

        System.out.println("\nTop 5 episódios: ");

        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}
