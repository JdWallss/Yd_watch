package com.aluracursos.ydwatch.service;

public interface IConvierteDatos {
    <T> T obtenerDatos (String json, Class<T> clase);
}
