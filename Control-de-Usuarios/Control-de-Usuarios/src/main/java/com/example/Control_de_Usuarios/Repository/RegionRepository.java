package com.example.Control_de_Usuarios.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Control_de_Usuarios.Model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {
    Region findByNombre(String nombre);

}
