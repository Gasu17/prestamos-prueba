package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.DuplicadoException;
import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.repository.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.util.List;
import java.util.Optional;

public class MaterialService {


    private Repository<Material> materialRepository;

    public MaterialService(Repository<Material> materialRepository) {
        this.materialRepository = materialRepository;
    }

    public void registrarMaterial(Material m) {
        if (m.getId() == null) {
            throw new IllegalArgumentException("El id es null");
        }
        String id = m.getId();
        Optional<Material> existe = materialRepository.findById(id);
        if (existe.isPresent()) {
            throw new DuplicadoException("Material Duplicado");


        }
        materialRepository.save(m);
    }

    void darDeBaja(String idMaterial) {
        Optional<Material> existe = materialRepository.findById(idMaterial);
        if (existe.isEmpty()) {
            throw new NoEncontradoException("El material no existe");
        }
        Material mat = existe.get();
        EstadoMaterial estado = mat.getEstadoMaterial();

        if (estado == EstadoMaterial.BAJA) {
            throw new MaterialNoDisponibleException("El material ya esta de baja");
        } else {
            mat.setEstadoMaterial(EstadoMaterial.BAJA);
        }


    }

    public List<Material> listar() {


        return materialRepository.listAll();
    }
}
