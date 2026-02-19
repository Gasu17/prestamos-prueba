package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Proyector extends Material {
    int lumens;

    public Proyector(String id, String nombre, EstadoMaterial estado, Set<String> etiquetas, int lumens) {
        super(id, nombre, estado, etiquetas);
        this.lumens = lumens;
    }


    @Override
    public String getTipo() {
        return "Proyector";
    }

    public int getLumens() {
        return lumens;
    }
}
