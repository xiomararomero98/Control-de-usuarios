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
@Table(name = "direcccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo que representa una dirección en el sistema")
public class Direccion {


 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    @Schema(description = "Identificador único de la dirección", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la calle", example = "Avenida Libertador")
    private String calle;

    @Column(nullable = false)
    @Schema(description = "Número de la dirección", example = "1234")
    private Integer numeracion;

    @Column
    @Schema(description = "Número del departamento o piso", example = "5B")
    private Integer numeroDepartamento;

    @Column
    @Schema(description = "Torre del edificio, si aplica", example = "Torre A")
    private String torre;

    @ManyToOne
    @JoinColumn(name = "Usuarios_id_usuarios", nullable = false)
    @JsonBackReference("usuario-direcciones")
    @Schema (description = "Usuario al que pertenece la dirección") 
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Comuna_id_comuna", nullable = false)
    @JsonBackReference("comuna-direcciones")
    @Schema(description = "Comuna a la que pertenece la dirección")
    private Comuna comuna;

}
