package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad que representa la clasificación de un valiente en el sistema.
 * Mapea la tabla "clasificacion_valiente" en la base de datos.
 */
@Entity
@Table(name = "clasificacion_valiente")
@Data
public class ClasificacionValiente {
    /**
     * Identificador único de la clasificación del valiente, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "nm_id_clasificacion_valiente")
    private int id;

    /**
     * Descripción de la clasificación del valiente.
     */
    @Column(name = "tx_clasificacion_valiente")
    @NotBlank(message = "La descripción de la clasificación es obligatoria.")
    @Size(max = 100, message = "La descripción de la clasificación no debe exceder los 100 caracteres.")
    private String descripcion;

    /**
     * Obtiene el identificador único de la clasificación del valiente.
     *
     * @return El ID de la clasificación.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único de la clasificación del valiente.
     *
     * @param id El nuevo ID de la clasificación.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la descripción de la clasificación del valiente.
     *
     * @return La descripción de la clasificación.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la clasificación del valiente.
     *
     * @param descripcion La nueva descripción de la clasificación.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}