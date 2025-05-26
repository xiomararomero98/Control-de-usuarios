package com.example.Control_de_Usuarios.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "direcccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long id;

    @Column
    private String calle;

    @Column
    private Integer numeracion;

    @Column
    private Integer numeroDepartamento;

    @Column
    private String torre;

    @ManyToOne
    @JoinColumn(name = "Usuarios_id_usuarios", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Comuna_id_comuna", nullable = false)
    private Comuna comuna;

}
