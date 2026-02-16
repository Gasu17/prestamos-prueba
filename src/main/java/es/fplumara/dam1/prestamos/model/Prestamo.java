package es.fplumara.dam1.prestamos.model;

import java.time.LocalDate;

public class Prestamo implements Identificable {
    String id;
    String idMaterial;
    String profesor;
    LocalDate fecha;

    public Prestamo(String id, String idMaterial, String profesor, LocalDate fecha) {
    }


    @Override
    public String getId() {
        return id;
    }
}

