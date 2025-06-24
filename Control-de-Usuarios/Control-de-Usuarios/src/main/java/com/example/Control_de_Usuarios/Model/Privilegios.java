package com.example.Control_de_Usuarios.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "privilegios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema (description = "Modelo que representa los privilegios del sistema")
public class Privilegios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_privilegios")
    @Schema(description = "Identificador único del privilegio", example = "1")
    private Long id;


    @Column(nullable = false)
    @Schema(description = "Nombre del privilegio", example = "Visualizar Perfumes")
    private String nombre;

    @OneToMany(mappedBy = "privilegio")
    @JsonManagedReference("privilegio-permisos")
    @Schema(description = "Lista de permisos asociados a este privilegio")
    private List <Permisos> permisosList;

}
