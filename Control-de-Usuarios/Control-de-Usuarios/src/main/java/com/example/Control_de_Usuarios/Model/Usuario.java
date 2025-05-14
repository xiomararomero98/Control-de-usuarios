package com.example.Control_de_Usuarios.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name="usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuarios;


    @Column
    private String nombre;


    @Column
    private String apellido;

    @Column
    private String correo;

    @Column
    private String clave;
    @Column
    private Date fecha_creacion;

    //Identificar tipo de relacion existente
    @ManyToOne //Tipo de relacion
    @JoinColumn(name="id_rol")
    @JsonIgnoreProperties("usuarios")
    private Rol rol;


}
