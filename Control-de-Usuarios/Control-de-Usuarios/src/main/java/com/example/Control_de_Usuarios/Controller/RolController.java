package com.example.Control_de_Usuarios.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Service.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Obtener todos los roles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Roles obtenidos correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay roles registrados")
    })
    @GetMapping
    public ResponseEntity<List<Rol>> obtenerRoles() {
        List<Rol> roles = rolService.obtenerRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Obtener un rol por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        try {
            Rol rol = rolService.obtenerRolPorId(id);
            return ResponseEntity.ok(rol);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Crear un nuevo rol")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rol creado correctamente")
    })
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        Rol nuevoRol = rolService.crearRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);
    }

    @Operation(summary = "Actualizar un rol por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody Rol rolActualizado) {
        try {
            Rol actualizado = rolService.actualizarRol(id, rolActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un rol por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
