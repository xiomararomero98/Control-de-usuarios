package com.example.Control_de_Usuarios.Model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
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
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String clave;

    @Column(name = "fecha_creacion", nullable = false)
    private Date fecha_creacion;

    @ManyToOne  
    @JoinColumn(name = "Rol_id_rol", nullable = false)
    @JsonIgnoreProperties("users")  // <- evita bucle
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("usuario")  // <- evita bucle
    private List<Direccion> direcciones;
}