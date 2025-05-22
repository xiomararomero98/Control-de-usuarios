package com.example.Control_de_Usuarios.Controller;

import java.util.List;

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

import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Service.RolService;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    //obtener todos los roles

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerRoles(){
        List<Rol> roles = rolService.obtenerRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(roles);
    }

    //obtener rol por Id

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id){
        try {
            Rol rol = rolService.obtenerRolPorId(id);
            return ResponseEntity.ok(rol);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    //Crear nuevo rol
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol){
        Rol nuevoRol = rolService.crearRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);

    }

    //Actualizar rol 

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody Rol rolActualizado){
        try {
            Rol actualizado = rolService.actualizarRol(id, rolActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //eliminar rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id){
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }


}
