package com.example.Control_de_Usuarios.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Control_de_Usuarios.Model.Permisos;
import com.example.Control_de_Usuarios.Service.PermisosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/permisos")
public class PermisosController {

    @Autowired
    private PermisosService permisosService;

    @Operation(summary = "Obtener todos los permisos", description = "Retorna una lista de todos los permisos registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permisos obtenidos exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay permisos registrados")
    })
    @GetMapping
    public ResponseEntity<List<Permisos>> obtenerPermisos() {
        List<Permisos> lista = permisosService.obtenerPermisos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener permiso por ID", description = "Obtiene un permiso específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Permisos> obtenerPermisoPorId(@PathVariable Long id) {
        try {
            Permisos permiso = permisosService.obtenerPermisoPorId(id);
            return ResponseEntity.ok(permiso);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Crear un nuevo permiso", description = "Permite crear un nuevo permiso en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Permiso creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear el permiso")
    })
    @PostMapping
    public ResponseEntity<Permisos> crearPermiso(@RequestBody Permisos permiso) {
        Permisos nuevoPermiso = permisosService.crearPermisos(permiso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPermiso);
    }

    @Operation(summary = "Actualizar permiso existente", description = "Actualiza un permiso por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPermiso(@PathVariable Long id, @RequestBody Permisos permisoActualizado) {
        try {
            Permisos actualizado = permisosService.actualizarPermiso(id, permisoActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un permiso", description = "Elimina un permiso específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Permiso eliminado exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error al eliminar el permiso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPermiso(@PathVariable Long id) {
        try {
            permisosService.eliminarPermisoPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}