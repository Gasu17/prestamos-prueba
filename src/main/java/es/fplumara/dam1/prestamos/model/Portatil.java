package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Portatil extends Material{
    int ramGB;

    public Portatil(String id, String nombre, EstadoMaterial estado, Set<String> etiquetas, int ramGB) {
        super(id,nombre,estado,etiquetas);
        this.ramGB = ramGB;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTipo() {
        return "Portatil";
    }

    public int getRamGB() {
        return ramGB;
    }
}
