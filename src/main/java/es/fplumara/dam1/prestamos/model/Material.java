package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public abstract class Material implements Identificable {
    String id;
    String nombre;
    EstadoMaterial estadoMaterial;
    Set<String> etiquetas;

    public Material(String id, String nombre, EstadoMaterial estadoMaterial, Set<String> etiquetas) {
        this.id = id;
        this.nombre = nombre;
        this.estadoMaterial = estadoMaterial;
        this.etiquetas = etiquetas;
    }




    public void setEtiquetas(Set<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estadoMaterial=" + estadoMaterial +
                ", etiquetas=" + etiquetas +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public EstadoMaterial getEstadoMaterial() {
        return estadoMaterial;
    }

    public Set<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEstadoMaterial(EstadoMaterial estadoMaterial) {
        this.estadoMaterial = estadoMaterial;

    }

    public abstract String getTipo();


}
