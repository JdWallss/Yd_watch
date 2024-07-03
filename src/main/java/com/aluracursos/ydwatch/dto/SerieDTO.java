package com.aluracursos.ydwatch.dto;

import com.aluracursos.ydwatch.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(Long id, 
            String titulo,
            Integer totalDeTemporadas,
            Double evaluacion,
            String sinopis,
            Categoria genero,
            String actores,
            String poster) {
}
