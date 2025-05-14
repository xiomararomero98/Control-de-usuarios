package com.example.Control_de_Usuarios.Service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional //permite hacer un rollback en caso de que la accion en la BD falle
public class UsuarioService {

}
