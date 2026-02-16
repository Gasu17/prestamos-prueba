package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PrestamoService {

    Repository<Material> materialRepository;
    Repository<Prestamo> prestamoRepository;

    public Prestamo crearPrestamo(String idMaterial, String profesor, LocalDate fecha) {

        return null;
    }

    void devolverMaterial(String idMaterial) {

    }

    List<Prestamo> listarPrestamos() {

        return List.of();
    }
}
