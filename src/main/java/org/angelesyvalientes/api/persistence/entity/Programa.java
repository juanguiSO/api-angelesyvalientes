package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
     * Estado del programa (activo/inactivo). Mapea a BIT(1) en la base de datos.
     */
    @Column(nullable = false, name = "bo_estado")
    private boolean estado;

    /**
     * Fecha del programa. No puede ser nulo en la base de datos ni en la aplicación.
     */
    @Column(nullable = false, name = "fe_fecha") // `nullable = false` para coincidir con `NN` en DB
    @NotNull(message = "La fecha del programa es obligatoria.") // ¡Nueva validación!
    private LocalDate fecha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre del programa es obligatorio.") @Size(max = 45, message = "El nombre del programa no debe exceder los 45 caracteres.") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre del programa es obligatorio.") @Size(max = 45, message = "El nombre del programa no debe exceder los 45 caracteres.") String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public @NotNull(message = "La fecha del programa es obligatoria.") LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(@NotNull(message = "La fecha del programa es obligatoria.") LocalDate fecha) {
        this.fecha = fecha;
    }
}