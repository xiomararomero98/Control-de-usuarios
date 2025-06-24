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

import com.example.Control_de_Usuarios.Model.Permisos;
import com.example.Control_de_Usuarios.Service.PermisosService;


@RestController
@RequestMapping("/api/v1/permisos")
public class PermisosController {

    @Autowired
    private PermisosService permisosService;

    //Obtener todos los permisos 
    @GetMapping
    public ResponseEntity<List<Permisos>>obtenerPermisos(){
        List<Permisos> lista = permisosService.obtenerPermisos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(lista);
    }

    //Obtener permisos por id
    @GetMapping("/{id}")
    public ResponseEntity<Permisos> obtenerPermisoPorId(@PathVariable Long id){
        try {
            Permisos permiso = permisosService.obtenerPermisoPorId(id);
            return ResponseEntity.ok(permiso);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Crear un nuevo permiso
    @PostMapping
    public ResponseEntity<Permisos> crearPermiso(@RequestBody Permisos permiso){
        Permisos nuevoPermiso= permisosService.crearPermisos(permiso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPermiso);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPermiso(@PathVariable Long id, @RequestBody Permisos permisoActualizado){
        try {
            Permisos actualizado= permisosService.actualizarPermiso(id, permisoActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

@DeleteMapping("/{id}")
public ResponseEntity<?> eliminarPermiso(@PathVariable Long id){
    try {
        permisosService.eliminarPermisoPorId(id);
        return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}


}
