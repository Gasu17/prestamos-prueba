package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.DuplicadoException;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.BaseRepository;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;

public class MaterialService {


    Repository<Material> materialRepository;


    void registrarMaterial(Material m) {
        String id = m.getId();
        if (String id.isEmpty())
        Optional<Material> mat = materialRepository.findById(id);
        if (mat.isPresent()) {
            throw new DuplicadoException("Material Duplicado");
        } else if (mat.isEmpty()) {
            throw new IllegalArgumentException("El id es null");
        }
        materialRepository.Save(m);

    }

    void darDeBaja(String idMaterial) {

    }

    public List<Material> listar() {

        return List.of();
    }
}
