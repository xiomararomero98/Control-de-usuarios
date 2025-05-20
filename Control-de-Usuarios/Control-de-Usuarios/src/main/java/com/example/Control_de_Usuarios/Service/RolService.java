package com.example.Control_de_Usuarios.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    //metodo para consultar todos los roles
    public List<Rol>obtenerRoles(){
        return rolRepository.findAll();
    }

    //metodo para buscar rol por id

    public Rol obtenerRolPorId(Long id){
        return rolRepository.findById(id)
        .orElseThrow(()-> new RuntimeException ("Rol no encontrado con ID:"+id ));
    }

    //metodo para crear un nuevo rol 

    public Rol crearRol(Rol rol){
        return rolRepository.save(rol);
    }

    //metodo para eliminar rol por id
    public void eliminarRol (Long id){
        rolRepository.deleteById(id);
    }

    //metodo para actualizar un rol existente por id

    public Rol actualizaRol(Long id, Rol rolActualizado){
        Rol rolExistente= rolRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID" + id));


        //actualizar los campos de rol exitente
        rolExistente.setNombre(rolActualizado.getNombre());

        //guardar el rol actualizado

        return rolRepository.save(rolExistente);
        }


}
