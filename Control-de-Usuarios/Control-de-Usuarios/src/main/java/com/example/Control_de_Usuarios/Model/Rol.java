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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("rol")  // <- evita bucle con usuarios
    private List<Usuario> users;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("rol")  // <- evita bucle con permisos
    private List<Permisos> permisos;

    public Rol(Long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}