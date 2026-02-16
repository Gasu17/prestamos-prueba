package es.fplumara.dam1.prestamos.repository;


import es.fplumara.dam1.prestamos.model.Identificable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseRepository<T extends Identificable> implements Repository<T> {
    Map<String, T> datos;

    @Override
    public void save(T e) {

    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<T> listAll() {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }
}
