package com.example.Control_de_Usuarios.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Control_de_Usuarios.Model.Permisos;
import com.example.Control_de_Usuarios.Repository.PermisosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PermisosService {


    @Autowired
    private PermisosRepository permisosRepository;

    //metodo para consultar todos los permisos

    public List <Permisos> obtenerPermisos(){
        return permisosRepository.findAll();
    }

    //metodo para buscar permsiso por ID

    public Permisos obtenerPermisoPorId(Long id){
        return permisosRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Permiso no encontrado con ID"+id));
    }

    //metodo para crear un nuevo permiso 

    public Permisos crearPermisos(Permisos permiso){
        return permisosRepository.save(permiso);
    }

    //metodo para actualizar un permiso existente
    
    public Permisos actualizarPermiso(Long id, Permisos permisoActualizado){
        Permisos permisoExistente = permisosRepository.findById(id)
        .orElseThrow(() ->new RuntimeException("Permiso no encontrado con ID:"+id));

        //Actualizar los campos del permiso existente

        permisoExistente.setPrivilegio(permisoActualizado.getPrivilegio());

        //guardar permiso actualizado

        return permisosRepository.save(permisoExistente);
    }

    //metodo para eliminar permisos por Id
    public void eliminarPermisoPorId(Long id){
        permisosRepository.deleteById(id);
    }

}
