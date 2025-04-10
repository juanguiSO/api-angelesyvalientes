package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa el tipo de donación en el sistema.
 * Mapea la tabla "tipo_donacion" en la base de datos.
 */
@Data
@Entity
@Table(name = "tipo_donacion")
@Getter
@Setter
public class TipoDonacion {

    /**
     * Identificador único del tipo de donación, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_tipo_donacion")
    private int id;

    /**
     * Descripción del tipo de donación.
     * La longitud máxima permitida para este campo es de 45 caracteres.
     */
    @Column(name = "tx_tipo_donacion", length = 45)
    @NotBlank(message = "La descripción del tipo de donación es obligatoria.")
    @Size(max = 45, message = "La descripción del tipo de donación no debe exceder los 45 caracteres.")
    private String tipoDonacion;

    /**
     * Establece el identificador único del tipo de donación.
     *
     * @param id El ID del tipo de donación.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la descripción del tipo de donación.
     *
     * @param tipoDonacion La descripción del tipo de donación.
     */
    public void setTipoDonacion(String tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    /**
     * Obtiene el identificador único del tipo de donación.
     *
     * @return El ID del tipo de donación.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la descripción del tipo de donación.
     *
     * @return La descripción del tipo de donación.
     */
    public String getTipoDonacion() {
        return tipoDonacion;
    }
}