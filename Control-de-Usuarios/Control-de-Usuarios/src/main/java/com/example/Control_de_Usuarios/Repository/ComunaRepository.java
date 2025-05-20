package com.example.Control_de_Usuarios.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Control_de_Usuarios.Model.Comuna;

@Repository 
public interface ComunaRepository extends JpaRepository<Comuna,Long> {

}
