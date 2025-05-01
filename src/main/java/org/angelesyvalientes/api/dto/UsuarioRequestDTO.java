package org.angelesyvalientes.api.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
    public class UsuarioRequestDTO {
        private String cdUsuario;
        private String txContrasena;
        private Long nmIdPersona; // ID de la persona
        private LocalDate feCreacion;
        private Boolean isDeleted;
        private Integer idRol; // Lista de IDs de roles

        public String getTxContrasena() {
            return txContrasena;
        }

        public LocalDate getFeCreacion() {
            return feCreacion;
        }

        public Boolean getDeleted() {
            return isDeleted;
        }


        public String getCdUsuario() {
            return cdUsuario;
        }

        public Integer getIdRol() {
            return idRol;
        }

        public Long getNmIdPersona() {
            return nmIdPersona;
        }

        public void setIdRol(Integer idRol) {
            this.idRol = idRol;
        }

        public void setNmIdPersona(Long nmIdPersona) {
            this.nmIdPersona = nmIdPersona;
        }

        public void setCdUsuario(String cdUsuario) {
            this.cdUsuario = cdUsuario;
        }
        public void setTxContrasena(String txContrasena) {
            this.txContrasena = txContrasena;
        }

        public void setFeCreacion(LocalDate feCreacion) {
            this.feCreacion = feCreacion;
        }

        public void setDeleted(Boolean deleted) {
            isDeleted = deleted;
        }
    }


