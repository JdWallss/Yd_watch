package com.aluracursos.ydwatch.repositorio;

import com.aluracursos.ydwatch.dto.EpisodioDTO;
import com.aluracursos.ydwatch.model.Categoria;
import com.aluracursos.ydwatch.model.Episodio;
import com.aluracursos.ydwatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Ejb3TransactionAnnotationParser;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {
    //Texto que JPA debe interpretar
   Optional<Serie> findByTituloContainsIgnoreCase (String nombreSerie);

   List<Serie> findTop5ByOrderByEvaluacionDesc();
   List<Serie> findByGenero(Categoria categoria);

   //Optional<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGraterThanEqual(int totalDeTemporadas, Double evaluacion);
   //Utilizacion del modelo JPQL y no Query nativa (Java persistence query language )
    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :totalDeTemporadas AND s.evaluacion >= :evaluacion")
   List<Serie> seriesPorTemporadaYEvaluacion(int totalDeTemporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    @Query("SELECT s FROM Serie s " + "JOIN s.episodios e " + "GROUP BY s " + "ORDER BY MAX(e.fechaDelanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadasPorNumero(Long id, Long numeroTemporada);
}


