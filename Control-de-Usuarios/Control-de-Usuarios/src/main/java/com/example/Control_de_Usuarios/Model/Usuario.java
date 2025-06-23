package com.example.Control_de_Usuarios.Model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
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
@Table(name = "Usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Modelo que representa un usuario del sistema")   
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuarios")
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    
    @Column(nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electrónico del usuario", example = "xio@duoc.cl")
    private String correo;

    @Column(nullable = false)
    @Schema(description = "Clave del usuario", example = "claveSegura123")
    private String clave;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del usuario", example = "2023-10-01T12:00:00Z")
    private Date fecha_creacion;

    @ManyToOne  
    @JoinColumn(name = "Rol_id_rol", nullable = false)
    @JsonIgnoreProperties("usuarios")
    @Schema(description = "Rol del usuario", example = "ADMINISTRADOR")
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-direcciones")
    @Schema(description = "Lista de direcciones asociadas al usuario")
    private List<Direccion> direcciones;
}
