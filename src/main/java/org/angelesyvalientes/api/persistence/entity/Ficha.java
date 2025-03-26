package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ficha")
@Getter
@Setter
public class Ficha {
    @Id
    @Column(name = "nm_id_ficha")
    private int id;

    @Column(name = "tx_tema", length = 45)
    private String nombre;

    @Column(name = "tx_url_recurso", length = 200)
    private String urlRecurso;

    @Column(name = "cod_ficha")
    private int codigo;

    @ManyToOne
    @JoinColumn(name = "nm_id_programa", nullable = false)
    private Programa programa;

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public void setUrlRecurso(String urlRecurso) {
        this.urlRecurso = urlRecurso;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getId() {
        return id;
    }

    public Programa getPrograma() {
        return programa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlRecurso() {
        return urlRecurso;
    }
}
