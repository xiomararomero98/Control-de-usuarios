package com.example.Control_de_Usuarios.Model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name="Usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuarios")
    private Long id;


    @Column
    private String nombre;


    @Column
    private String apellido;

    @Column
    private String correo;

    @Column
    private String clave;


    @Column(name = "fecha_creacion", nullable = false)
    private Date fecha_creacion;

    //Identificar tipo de relacion existente
    @ManyToOne //Tipo de relacion
    @JoinColumn(name="Rol_id_rol")
    @JsonIgnoreProperties("usuarios")
    private Rol rol;
    //relacion con direccion
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Direccion> direcciones;


}
