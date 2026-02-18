package es.fplumara.dam1.prestamos.app;

import es.fplumara.dam1.prestamos.csv.CSVMaterialExporter;
import es.fplumara.dam1.prestamos.csv.CSVMaterialImporter;
import es.fplumara.dam1.prestamos.csv.RegistroMaterialCsv;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Portatil;
import es.fplumara.dam1.prestamos.model.Proyector;
import es.fplumara.dam1.prestamos.repository.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.PrestamoRepositoryImpl;
import es.fplumara.dam1.prestamos.service.MaterialService;
import es.fplumara.dam1.prestamos.service.PrestamoService;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Main de ejemplo para demostrar el flujo mínimo del examen (sin menú complejo).
 * La idea es que este método ejecute una "demo" por consola.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Examen DAM1 - Préstamo de Material (Java 21)");

        MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl();
        PrestamoRepositoryImpl prestamoRepository = new PrestamoRepositoryImpl();

        CSVMaterialImporter importer = new CSVMaterialImporter();
        CSVMaterialExporter exporter = new CSVMaterialExporter();
        MaterialService materialService = new MaterialService(materialRepository);
        PrestamoService prestamoService = new PrestamoService(materialRepository, prestamoRepository);


        List<RegistroMaterialCsv> datos = importer.leer("data/materiales.csv");

        for (RegistroMaterialCsv r : datos) {
            EstadoMaterial estado = EstadoMaterial.valueOf(r.estado());
            if (r.tipo().equalsIgnoreCase("Portatil")) {

                Portatil portatil = new Portatil(
                        r.id(),
                        r.nombre(),
                        estado,
                        r.etiquetas(),
                        r.extra()
                );
                materialService.registrarMaterial(portatil);
            } else if (r.tipo().equalsIgnoreCase("Proyector")) {

                Proyector proyector = new Proyector(
                        r.id(),
                        r.nombre(),
                        estado,
                        r.etiquetas(),
                        r.extra()
                );
                materialService.registrarMaterial(proyector);

            }


        }

        String idMaterial = "M001";


        prestamoService.crearPrestamo(idMaterial, "ivan", LocalDate.now());

        Material mat = materialRepository.findById(idMaterial)
                .orElseThrow(() -> new NoEncontradoException("El material no ha sido encontrado"));

        if (mat.getEstadoMaterial() != EstadoMaterial.PRESTADO) {
            throw new NoEncontradoException("Material no cambiado a prestado");

        }


        materialService.listar().forEach(m -> {
            System.out.println("ID: " + m.getId());

            System.out.println("Nombre: " + m.getNombre());

            System.out.println("Tipo: " + m.getTipo());
            System.out.println("Estado: " + m.getEstadoMaterial());
            System.out.println("Extra: " + m.getEtiquetas());
            System.out.println("=======================================");
        });

        prestamoService.listarPrestamos().forEach(m -> {
            System.out.println("ID: " + m.getId());
            System.out.println("idMaterial: " + m.getIdMaterial());
            System.out.println("profesor: " + m.getProfesor());
            System.out.println("fecha: " + m.getFecha());
        });

        prestamoService.devolverMaterial(idMaterial);
        System.out.println(materialRepository.findById(idMaterial).get().getEstadoMaterial());


        List<Material> materiales = materialRepository.listAll();
        List<RegistroMaterialCsv> csvs = new ArrayList<>();

        for (Material m : materiales) {
            if (m instanceof Portatil) {
                RegistroMaterialCsv registroMaterialCsv = new RegistroMaterialCsv(
                        m.getTipo(),
                        m.getId(),
                        m.getNombre(),
                        m.getEstadoMaterial().toString(),
                        ((Portatil) m).getRamGB(),
                        m.getEtiquetas()
                );
                csvs.add(registroMaterialCsv);
            } else if (m instanceof Proyector) {
                RegistroMaterialCsv registroMaterialCsv = new RegistroMaterialCsv(
                        m.getTipo(),
                        m.getId(),
                        m.getNombre(),
                        m.getEstadoMaterial().toString(),
                        ((Proyector) m).getLumens(),
                        m.getEtiquetas()
                        );
                csvs.add(registroMaterialCsv);
            }

        }

        exporter.escribir("data/materiales.csv", csvs);
//
        /*
         * FLUJO MÍNIMO OBLIGATORIO (lo que debe hacer tu main)
         *
         * 1) Crear repositorios en memoria
         *    - Crear MaterialRepositoryImpl (almacena materiales en memoria).
         *    - Crear PrestamoRepositoryImpl (almacena préstamos en memoria).
         *
         * 2) Crear servicios
         *    - Crear MaterialService usando el repositorio de materiales.
         *    - Crear PrestamoService usando el repositorio de materiales y el de préstamos.
         *
         * 3) Cargar materiales desde CSV (código proporcionado)
         *    - Usar CsvMaterialImporter para leer "materiales.csv".
         *    - El importer devuelve registros (por ejemplo RegistroMaterialCsv).
         *    - Convertir cada registro a tu modelo:
         *        - Si tipo == "PORTATIL" -> crear Portatil (extra = ramGB)
         *        - Si tipo == "PROYECTOR" -> crear Proyector (extra = lumens)
         *      (aplicando estado y etiquetas)
         *    - Registrar cada Material llamando a MaterialService.registrarMaterial(...)


         *
         *
         *
         * 4) Crear un préstamo
         *    - Elegir un id de material existente (por ejemplo "M001").
         *    - Llamar a PrestamoService.crearPrestamo("M001", "Nombre Profesor", fecha)
         *    - Comprobar que el material pasa a estado PRESTADO
         *
         * 5) Listar por consola
         *    - Imprimir todos los materiales (MaterialService.listar()) mostrando: id, nombre, estado, tipo.
         *    - Imprimir todos los préstamos (PrestamoService.listarPrestamos()) mostrando: id, idMaterial, profesor, fecha.
         *
         * 6) Devolver el material
         *    - Llamar a PrestamoService.devolverMaterial("M001")
         *    - Comprobar que vuelve a estado DISPONIBLE
         *
         * 7) Exportar a CSV (código proporcionado)
         *    - Convertir tu lista de Material a la estructura que pida el exporter (por ejemplo RegistroMaterialCsv).
         *    - Usar CsvMaterialExporter para escribir "salida_materiales.csv".
         *
         * Nota:
         * - No hace falta interfaz, ni menú, ni pedir datos por teclado: valores fijos y salida por consola es suficiente.
         */
    }
}