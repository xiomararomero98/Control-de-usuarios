package com.example.Control_de_Usuarios.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    private Long id;

    @Column(name = "nombre_comuna", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "Region_id_region", nullable = false)
    @JsonIgnoreProperties("comunas")  // <- evita bucle con Region
    private Region region;

    @OneToMany(mappedBy = "comuna")
    @JsonIgnoreProperties("comuna")  // <- evita bucle con Direccion
    private List<Direccion> direcciones;
}
