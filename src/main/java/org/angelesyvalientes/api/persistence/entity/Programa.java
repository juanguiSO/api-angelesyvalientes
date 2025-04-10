package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un programa dentro del sistema.
 * Mapea la tabla "programa" en la base de datos.
 */
@Entity
@Table(name = "programa")
@Getter
@Setter
public class Programa {
    /**
     * Identificador único del programa, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_programa")
    private int id;

    /**
     * Nombre del programa. No puede ser nulo y tiene una longitud máxima de 45 caracteres.
     */
    @Column(nullable = false, name = "tx_programa", length = 45)
    @NotBlank(message = "El nombre del programa es obligatorio.")
    @Size(max = 45, message = "El nombre del programa no debe exceder los 45 caracteres.")
    private String nombre;

    /**
     * Estado del programa. No puede ser nulo y se define como un Decimal.
     * (Nota: La definición `Decimal(5,2)` en `columnDefinition` sugiere un número decimal,
     * pero el tipo en Java es `String`. Esto podría requerir una conversión o ajuste
     * según la lógica de la aplicación).
     */
    @Column(nullable = false, name = "bo_estado", columnDefinition = "Decimal(5,2)")
    @NotBlank(message = "El estado del programa es obligatorio.")
    // Considerar si se necesita validación numérica para el formato decimal
    // Si se espera un número dentro de un rango específico:
    // @DecimalMin(value = "0.00", inclusive = true, message = "El estado debe ser mayor o igual a 0.00")
    // @DecimalMax(value = "999.99", inclusive = true, message = "El estado debe ser menor o igual a 999.99")
    private String estado;

    /**
     * Tema o área principal del programa (opcional). Tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_tema", length = 45)
    @Size(max = 45, message = "El tema del programa no debe exceder los 45 caracteres.")
    private String tema;

    /**
     * Establece el identificador único del programa.
     *
     * @param id El ID del programa.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre del programa.
     *
     * @param nombre El nombre del programa.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el estado del programa.
     *
     * @param estado El estado del programa.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Establece el tema del programa.
     *
     * @param tema El tema del programa.
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * Obtiene el estado del programa.
     *
     * @return El estado del programa.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el identificador único del programa.
     *
     * @return El ID del programa.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del programa.
     *
     * @return El nombre del programa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el tema del programa.
     *
     * @return El tema del programa.
     */
    public String getTema() {
        return tema;
    }
}