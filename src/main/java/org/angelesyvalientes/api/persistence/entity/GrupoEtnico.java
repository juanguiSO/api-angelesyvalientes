package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad que representa un grupo poblacional en el sistema.
 * Mapea la tabla "grupo_poblacional" en la base de datos.
 */
@Entity
@Table(name = "grupo_etnico")
@Data
public class GrupoEtnico {
    /**
     * Identificador único del grupo poblacional, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "nm_id_grupo_etnico")
    private int id;

    /**
     * Descripción del grupo poblacional.
     */
    @Column(name = "tx_grupo_etnico")
    @NotBlank(message = "La descripción del grupo poblacional es obligatoria.")
    @Size(max = 100, message = "La descripción del grupo poblacional no debe exceder los 100 caracteres.")
    private String descripcion;

    /**
     * Establece el identificador único del grupo poblacional.
     *
     * @param id El nuevo ID del grupo poblacional.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la descripción del grupo poblacional.
     *
     * @param descripcion La nueva descripción del grupo poblacional.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el identificador único del grupo poblacional.
     *
     * @return El ID del grupo poblacional.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la descripción del grupo poblacional.
     *
     * @return La descripción del grupo poblacional.
     */
    public String getDescripcion() {
        return descripcion;
    }
}