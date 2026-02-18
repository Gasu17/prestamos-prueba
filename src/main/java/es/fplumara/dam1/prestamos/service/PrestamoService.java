package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.PrestamoRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PrestamoService {

    Repository<Material> materialRepository;
    Repository<Prestamo> prestamoRepository;

    public PrestamoService(Repository<Material> materialRepository, Repository<Prestamo> prestamoRepository) {
        this.materialRepository = materialRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo crearPrestamo(String idMaterial, String profesor, LocalDate fecha) {
        if (idMaterial == null || profesor == null || fecha == null) {
            throw new IllegalArgumentException("No puede haber campos nulos");
        }

        Material existe = materialRepository.findById(idMaterial)
                .orElseThrow(() -> new NoEncontradoException("Material no encontrado"));

        if (existe.getEstadoMaterial() != EstadoMaterial.DISPONIBLE) {
            throw new MaterialNoDisponibleException("Material no disponible");

        }
        String id = UUID.randomUUID().toString();
        Prestamo prestamo = new Prestamo(id, idMaterial, profesor, fecha);

        prestamoRepository.save(prestamo);
        existe.setEstadoMaterial(EstadoMaterial.PRESTADO);
        materialRepository.save(existe);

        return prestamo;
    }

    public void devolverMaterial(String idMaterial) {
        if (idMaterial == null || idMaterial.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Material existe = materialRepository.findById(idMaterial)
                .orElseThrow(() -> new NoEncontradoException("Material no encontrado"));
        if (existe.getEstadoMaterial() != EstadoMaterial.PRESTADO) {
            throw new MaterialNoDisponibleException("Material prestado");
        }

        existe.setEstadoMaterial(EstadoMaterial.DISPONIBLE);
        materialRepository.save(existe);
    }

    public List<Prestamo> listarPrestamos() {

        return prestamoRepository.listAll();
    }
}
