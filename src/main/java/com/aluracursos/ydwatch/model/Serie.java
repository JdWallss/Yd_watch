package com.aluracursos.ydwatch.model;

import com.aluracursos.ydwatch.service.ConsultaChatGPT;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
@Entity
@Table(name = "series")
public class Serie {
    @Id
    //Se encarga de decir cual es 1, 2, 3 y así
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private Integer totalDeTemporadas;
    private Double evaluacion;
    private String sinopis;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String actores;
    private String poster;
    //Relacionamos esta entidad con Episodios, siendo Episodios el propietario
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    //Constructor predeterminado obligatorio por JPA
    public Serie (){}
    public Serie(DatosSerie datosSerie){
        this.titulo = datosSerie.titulo();
        this.totalDeTemporadas = datosSerie.totalDeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion()))
                .orElse(0);
        this.poster = datosSerie.poster();
        this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
        this.actores = datosSerie.actores();
        //Sin utilizar chatGPT
        this.sinopis = datosSerie.sinopis();
        //Con chatGPT
        //this.sinopis = ConsultaChatGPT.obtenerTraduccion(datosSerie.sinopis());
        
    }

    //ToString

    @Override
    public String toString() {
        return ", titulo='" + titulo + '\'' +
                " genero=" + genero +
                ", totalDeTemporadas=" + totalDeTemporadas +
                ", evaluacion=" + evaluacion +
                ", sinopis='" + sinopis + '\'' +
                ", actores='" + actores + '\'' +
                ", poster='" + poster + '\'' +
                ", episodios='" + episodios + '\''  ;
    }

    //Getters & Setters

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        //Linea para relacionar todos los episodios con el id de serie correcto
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getSinopis() {
        return sinopis;
    }

    public void setSinopis(String sinopis) {
        this.sinopis = sinopis;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
