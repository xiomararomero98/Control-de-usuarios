package com.example.Control_de_Usuarios.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Repository.RolRepository;
import com.example.Control_de_Usuarios.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional //permite hacer un rollback en caso de que la accion en la BD falle
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    //Obtener todos los usuarios 

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }


    //Obtener usuarios por Id 

    public Optional<Usuario> obtenerUsuarioPorId(Long id){
        return usuarioRepository.findById(id);
    }


    //metodo para agregar un Usuario

    public Usuario crearUsuario(String nombre, String apellido, String correo, String clave, Long idRol){

        Rol rol =rolRepository.findById(idRol)
        .orElseThrow(()->new RuntimeException("Rol no existe con ID:"+ idRol));

        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setClave(clave);
        usuario.setCorreo(correo);
        usuario.setRol(rol);
        usuario.setFecha_creacion(new Date()); //fecha de creacion actual

        return usuarioRepository.save(usuario);

    }

    //Eliminar usuario por Id 

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    //actualizar un usuario existente

   public Optional<Usuario> actualizarUsuario(Long id, Usuario usuarioActualizado) {
    Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

    if (optionalUsuario.isPresent()) {
        Usuario usuario = optionalUsuario.get();

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        usuario.setClave(usuarioActualizado.getClave());

        // Validación para evitar NullPointerException
        if (usuarioActualizado.getRol() == null || usuarioActualizado.getRol().getId() == null) {
            throw new RuntimeException("El Rol o su ID no pueden ser nulos.");
        }

        Rol rol = rolRepository.findById(usuarioActualizado.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + usuarioActualizado.getRol().getId()));

        usuario.setRol(rol);

        usuarioRepository.save(usuario);
    }

    return optionalUsuario;
}

    }





