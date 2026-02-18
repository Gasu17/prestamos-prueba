package es.fplumara.dam1.prestamos.repository;


import es.fplumara.dam1.prestamos.csv.CSVMaterialImporter;
import es.fplumara.dam1.prestamos.csv.RegistroMaterialCsv;
import es.fplumara.dam1.prestamos.model.Identificable;

import java.nio.file.Path;
import java.util.*;


public class BaseRepository<T extends Identificable> implements Repository<T> {


    Map<String, T> datos = new HashMap<>();


    @Override
    public void save(T e) {

        datos.put(e.getId(), e);

    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<T> listAll() {
        return new ArrayList<>(datos.values());

    }

    @Override
    public void delete(String id) {
        datos.remove(id);

    }
}
