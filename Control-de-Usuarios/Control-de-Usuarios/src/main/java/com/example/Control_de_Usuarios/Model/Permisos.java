package com.example.Control_de_Usuarios.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Permisos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa los permisos del sistema")
public class Permisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    @Schema(description = "Identificador único del permiso", example = "1")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "Privilegios_id_privilegios", nullable = false)
    @JsonBackReference("privilegio-permisos")
    @Schema(description = "Privilegio asociado a este permiso")
    private Privilegios privilegio;

    @ManyToOne
    @JoinColumn(name = "Rol_id_rol", nullable = false)
    @JsonBackReference("rol-permisos")
    @Schema(description = "Rol asociado a este permiso")
    private Rol rol;
}
