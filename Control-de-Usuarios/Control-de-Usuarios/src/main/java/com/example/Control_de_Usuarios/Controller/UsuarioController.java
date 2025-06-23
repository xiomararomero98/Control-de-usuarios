package com.example.Control_de_Usuarios.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //obtener todos los usuarios

    @GetMapping
    public ResponseEntity<List<Usuario>>obtenerUsuarios(){
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    //obtener usuarios por Id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id){
        Optional <Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    //crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario){
        try {
            Long idRol= usuario.getRol().getId(); //accede al id del rol
            Usuario userNuevo= usuarioService.crearUsuario(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getClave(),
                idRol
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(userNuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //actualizar un usuario
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?>actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado){
        try {
             Optional <Usuario> usuario =usuarioService.actualizarUsuario(id, usuarioActualizado);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
                
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID"+id);
                
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el usuario"+ e.getMessage());
        }
    }

    //eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> getUsuarioPorCorreo(@PathVariable String correo) {
    Optional<Usuario> usuario = usuarioService.findByCorreo(correo);
    return usuario.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
}


}
