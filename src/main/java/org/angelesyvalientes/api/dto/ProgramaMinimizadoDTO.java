package org.angelesyvalientes.api.dto;

public class ProgramaMinimizadoDTO {
    private int id;
    private String nombre;
    private boolean matriculado;


    public ProgramaMinimizadoDTO(int id, String nombre, boolean matriculado) {
        this.id = id;
        this.nombre = nombre;
        this.matriculado = matriculado;


    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }
}