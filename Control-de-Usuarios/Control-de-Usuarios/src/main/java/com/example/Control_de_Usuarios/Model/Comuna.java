package com.example.Control_de_Usuarios.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comuna")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema (description = "Modelo que representa una comuna en el sistema")
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    @Schema(description = "Identificador único de la comuna", example = "1")
    private Long id;

    @Column(name = "nombre_comuna", nullable = false)
    @Schema(description = "Nombre de la comuna", example = "Santiago")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "Region_id_region", nullable = false)
    @JsonBackReference("region-comunas")
    @Schema(description = "Región a la que pertenece la comuna")
    private Region region;

    @OneToMany(mappedBy = "comuna")
    @JsonManagedReference("comuna-direcciones")
    @Schema(description = "Lista de direcciones asociadas a la comuna")
    private List<Direccion> direcciones;

}
