package es.fplumara.dam1.prestamos.service;


import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.model.Proyector;
import es.fplumara.dam1.prestamos.repository.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.PrestamoRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.Repository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrestamosServiceTest {

    // TODO (alumnos): añadir JUnit 5 y Mockito en el pom.xml y completar:
    //
    // - crearPrestamo_ok_cambiaEstado_y_guarda()

    @Mock
    Repository<Prestamo> prestamoRepository;
    @Mock
    Repository<Material> materialRepository;
    @InjectMocks
    PrestamoService prestamoService;

    Material proyector;

    @BeforeEach
    void setup() {
        proyector = new Proyector(
                "123",
                "ejemploPoryectir",
                EstadoMaterial.DISPONIBLE,
                Set.of("eitqueta", "asd"),
                23

        );
    }


    @Test
    void crearPrestamo_ok_cambiaEstado() {
        System.out.println(prestamoService.materialRepository.getClass());
        System.out.println(materialRepository.getClass());

        System.out.println(proyector.getId());
        when(materialRepository.findById(anyString()))
                .thenReturn(Optional.of(proyector));


        Prestamo resultado = prestamoService.crearPrestamo(
                proyector.getId(),
                "ivan",
                LocalDate.now()
        );
        //ASSERT
        assertEquals(EstadoMaterial.PRESTADO, proyector.getEstadoMaterial());
        // verify(prestamoRepository).save(any());
    }

    // - crearPrestamo_materialNoExiste_lanzaNoEncontrado()
    @Test
    void crearPrestamo_materialNoExiste_lanzaNoEncontrado() {


        when(materialRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NoEncontradoException.class, () -> {
            prestamoService.crearPrestamo("123", "ivan", LocalDate.now());
        });
    }


    // - crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible()
    @Test
    void crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible() {

        when(materialRepository.findById(anyString()))
                .thenReturn(Optional.of(proyector));

        assertThrows(MaterialNoDisponibleException.class, () -> {
            prestamoService.crearPrestamo(proyector.getId(), "IVAN", LocalDate.now());
        });
    }


    // - devolverMaterial_ok_cambiaADisponible()
    @Test
    void devolverMaterial_ok_cambiaADisponible() {
        when(materialRepository.findById(anyString()))
                .thenReturn(Optional.of(proyector));

        EstadoMaterial estado = EstadoMaterial.PRESTADO;
        Prestamo prestamo = prestamoService.crearPrestamo(proyector.getId(), "Ivan0", LocalDate.now());
        assertEquals(estado, () -> {
            );
        } );
    }
    // Requisito: usar mocks de repositorios y verify(...)
}
