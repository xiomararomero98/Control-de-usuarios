package com.example.Control_de_Usuarios.Model;

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
@Table(name = "Permisos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "Privilegios_id_privilegios", nullable = false)
    private Privilegios privilegio;

    @ManyToOne
    @JoinColumn(name = "Rol_id_rol", nullable = false)
    private Rol rol;
}
