package com.example.Control_de_Usuarios.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
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
import java.util.List;

@Entity
@Table(name = "Rol")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Modelo que representa un rol en el sistema")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    @Schema(description = "Identificador único del rol", example = "1")
    private Long id;
    


    @Column
    @Schema(description = "Nombre del rol", example = "Administrador")
    private String nombre;


    //identificar relacion con usuarios
    @OneToMany(mappedBy = "rol", cascade =CascadeType.ALL)
    @JsonIgnoreProperties("rol")
    @Schema(description = "Lista de usuarios asociados a este rol")
    private List <Usuario> users;

    //identificar relacion con permisos

    @OneToMany(mappedBy = "rol", cascade =CascadeType.ALL)
    @JsonIgnoreProperties("rol")
    @Schema(description = "Lista de permisos asociados a este rol")
    private List <Permisos> permisos;

    //carga de datos

    public Rol(Long id, String nombre){
        this.id =id;
        this.nombre= nombre;
    }

}