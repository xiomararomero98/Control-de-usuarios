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
@Table(name = "region")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa una región en el sistema")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    @Schema(description = "Identificador único de la región", example = "1")
    private Long id;

    @Column(name = "nombre_region", nullable = false)
    @Schema(description = "Nombre de la región", example = "Metropolitana")
    private String nombre;

    //identificar relacion con comunas
    @OneToMany(mappedBy = "region")
    @JsonManagedReference("region-comunas")
    @Schema(description = "Lista de comunas asociadas a la región")
    private List<Comuna> comunas;

}
