package com.example.Control_de_Usuarios.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Repository.PrivilegiosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PrivilegiosService {

    @Autowired
    private PrivilegiosRepository privilegiosRepository;

    //Obtener todos los privilegios

    public List <Privilegios>obtenerPrivilegios(){
        return privilegiosRepository.findAll();
    }

    //Obtener privilegio por ID
    public Optional<Privilegios> obtenerPrivilegioPorId(Long id){
        return privilegiosRepository.findById(id);
    }

    //Crear un nuevo privilegio
    public Privilegios creaPrivilegio(Privilegios privilegio){
        return privilegiosRepository.save(privilegio);
    }

    //eliminar Privilegio por ID

    public void eliminarPrivilegio(Long id){
        privilegiosRepository.deleteById(id);
    }

    //Actualizar un privilegio existente




}
