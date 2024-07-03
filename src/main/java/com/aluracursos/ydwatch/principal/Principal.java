package com.aluracursos.ydwatch.principal;
import com.aluracursos.ydwatch.model.*;
import com.aluracursos.ydwatch.repositorio.SerieRepository;
import com.aluracursos.ydwatch.service.ConsumoAPI;
import com.aluracursos.ydwatch.service.ConvierteDatos;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
//    private Scanner teclado = new Scanner(System.in);
//    private ConsumoAPI consumoApi = new ConsumoAPI();
//    private final String URL_Base = "https://www.omdbapi.com/?t=";
//    private final String API_KEY = "&apikey=5ecfd3bd";
//    private ConvierteDatos conversor = new ConvierteDatos();
//
//    public void muestraElMenu(){
//        System.out.println("Porfavor escriba el nombre de la serie que desee informacion");
//        // Busca los datos generales de las series
//        var nombreSerie = teclado.nextLine();
//        var json = consumoApi.obtenerDatos(URL_Base + nombreSerie.replace(" ", "+") + API_KEY);
//        var datos = conversor.obtenerDatos(json, DatosSerie.class);
//        System.out.println(datos);
//
//        //Busca os datos de todas las temporadas
//        List<DatosTemporadas> temporadas = new ArrayList<>();
//        for (int i = 1; i < datos.totalDeTemporadas() ; i++) {
//            json= consumoApi.obtenerDatos(URL_Base + nombreSerie.replace(" ", "+") +"&Season=" + i + API_KEY);
//            var datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
//            temporadas.add(datosTemporada);
//        }
//       // temporadas.forEach(System.out::println);
//
//        //Mostrar solo el titulo de los episodios para las temporadas forma larga
//       /* for (int i = 0; i < datos.totalDeTemporadas() ; i++) {
//            List<DatosEpisodio> episodiosTemporada = new temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j));
//            }
//        }*/ //Metodo Lambda
//       // temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//        // Convertir todas las informaciones a una lista del tipo DatosEpisodio
//
//        List<DatosEpisodio> datosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());
//
//
//
//        // Top 5 episodios
//        System.out.println("Top 5 episodios");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro N/A" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo Filtro M>m" + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("tercer Filtro Mayusculas" + e))
//                .limit(5)
//                .forEach(System.out::println);
//
//        //Conviertieneo los datos a una listad del tipo Episodio
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                    .map(d -> new Episodio(t.numero(),d)))
//                .limit(5)
//                .collect(Collectors.toList());
//
//        episodios.forEach(System.out::println);
//
//        //Busqueda de episodios a partir de x año
//        System.out.println("Indica el año a partir del cual deseas ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDelanzamiento() != null && e.getFechaDelanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada" + e.getTemporada() +
//                                " Episodio" + e.getTitulo() +
//                                " Feche de lanzamiento" + e.getFechaDelanzamiento().format(dtf)
//                ));
//
//        //Busca episodios por pedazo del titulo
////        System.out.println("Por favor escriba el titulo del episodio que desea ver");
////        var pedazoTitulo = teclado.nextLine();
////        Optional<Episodio> episodioBuscado = episodios.stream()
////                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
////                .findFirst();
////        if (episodioBuscado.isPresent()){
////            System.out.println(" Episodio encontrado");
////            System.out.println("Los datos son: " + episodioBuscado.get());
////        }else {
////            System.out.println("Episodio no encontrado");
////        }
//
//        Map<Integer, Double> evaluacionesPortemporada = episodios.stream()
//                .filter(e -> e.getEvaluacion() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getEvaluacion)));
//        System.out.println(evaluacionesPortemporada);
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getEvaluacion() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
//        System.out.println("Media de las evaluaciones: " + est.getAverage());
//        System.out.println("Episodio mejor evaluado: " + est.getMin());
//
//
//    }

            //Codigo refactorizado de Alura, 2o Curso

private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=5ecfd3bd";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repository;
    private  List<Serie> series;
    private Optional <Serie> serieBuscada;


    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series desde la API
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series guardadas por titulo
                    5 - Top 5 mejores series
                    6 - Buscar Serie por categoria
                    7 - Filtrar por numero de temporadas y evaluaciones
                    8 - Buscar por episodio
                    9 - Top 5 episodios por serie
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSerieCategoria();
                    break;
                case 7:
                    buscarSeriesPorTemporadasYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }
    private void buscarEpisodioPorSerie() {
       mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()){
            var serieEncontrada = serie.get();
            // DatosSerie datosSerie = getDatosSerie();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);

        }
    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        //Guardar en el repositorio
        Serie serie = new Serie(datos);
        repository.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        series = repository.findAll();
                //Metodo sin base de datos, creando una lista local
                new ArrayList<>();
        series = datosSeries.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo(){
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()){
            System.out.println("la serie buscada es: " + serieBuscada.get());
        }else {
            System.out.println("Serie no encontrada");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repository.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s ->
                System.out.println("Serie: " + s.getTitulo() + " Evaluacion: " + s.getEvaluacion()));
    }

    private void buscarSerieCategoria(){
        System.out.println("Escriba el genero/categoria de la serie que desea buscar");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repository.findByGenero(categoria);
        System.out.println("Las series de la categroia " + genero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarSeriesPorTemporadasYEvaluacion(){
        System.out.println("Escriba la cantidad de temporadas por la que desea buscar");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();
        System.out.println("A partir de que valor de evaluacion?");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();
        List<Serie> filtroSerie = repository.seriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);
        System.out.println("*** Series filtradas ***");
        filtroSerie.forEach(s -> System.out.println(s.getTitulo() + " - evaluacion: " + s.getEvaluacion()));
    }

    private void buscarEpisodiosPorTitulo(){
        System.out.println("Escribe el nombre del episodio que deseas buscar");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repository.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Serie: %s episodio %s temporada %s Evaluacion %s\n",
                       e.getSerie(), e.getNumeroEpisodio(), e.getTemporada(), e.getEvaluacion()));
    }

    private void buscarTop5Episodios(){
        buscarSeriesPorTitulo();
        if (serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repository.top5Episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Serie: %s episodio %s temporada %s Evaluacion %s\n",
                            e.getSerie(), e.getTitulo(), e.getTemporada(), e.getEvaluacion()));
        }
    }
}
