package es.fplumara.dam1.prestamos.service;


import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.repository.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.PrestamoRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.Repository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

class PrestamosServiceTest {

    // TODO (alumnos): añadir JUnit 5 y Mockito en el pom.xml y completar:
    //
    // - crearPrestamo_ok_cambiaEstado_y_guarda()
    @Mock
    PrestamoRepositoryImpl prestamoRepository;
    @Mock
    MaterialRepositoryImpl materialRepository;
    @InjectMocks
    PrestamoService prestamoService;


    @Test
    void crearPrestamo_ok_cambiaEstado (){
        String id = "1";
        String profesor = "profe";
        LocalDate fecha = LocalDate.now();


        //definir lo que te tiene que devolver el repository
        when(prestamoRepository.findById())
        //comprobar crear el prestamo
        // verificar que se guarde
    }

    // - crearPrestamo_materialNoExiste_lanzaNoEncontrado()
    // - crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible()
    // - devolverMaterial_ok_cambiaADisponible()
    //
    // Requisito: usar mocks de repositorios y verify(...)
}
