package com.example.Control_de_Usuarios.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "privilegios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Privilegios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_privilegios")
    private Long id;


    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "privilegio")
    @JsonManagedReference("privilegio-permisos")
    private List <Permisos> permisosList;

}
