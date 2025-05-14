package com.example.Control_de_Usuarios.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Control_de_Usuarios.Model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {

}
