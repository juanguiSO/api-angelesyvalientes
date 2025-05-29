package org.angelesyvalientes.api.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValienteCumpleanosDTO {

    private int idPersona;
    private String segundoNombre;
    private String nombres;
    private String apellidos;
    private String segundoApellido;
    private LocalDate fechaNacimiento;


    public ValienteCumpleanosDTO(int idPersona, String nombres, String segundoNombre,
                                 String apellidos, String segundoApellido, LocalDate fechaNacimiento) {
        this.idPersona = idPersona;
        this.nombres = nombres;
        this.segundoNombre = segundoNombre;
        this.apellidos = apellidos;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
    }


    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public String getSegundoApellido() {
        return segundoApellido;
           }

    public String getSegundoNombre() {
        return segundoNombre;
    }
}
