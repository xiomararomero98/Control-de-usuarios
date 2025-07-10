package com.example.Control_de_Usuarios.Controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Control_de_Usuarios.Model.Direccion;
import com.example.Control_de_Usuarios.Service.DireccionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @Operation(summary = "Obtener todas las direcciones", description = "Devuelve una lista con todas las direcciones registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Direcciones encontradas correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay direcciones registradas")
    })
    @GetMapping
    public ResponseEntity<List<Direccion>> obtenerDirecciones() {
        List<Direccion> direcciones = direccionService.obtenerTodasLasDirecciones();
        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(direcciones);
    }

    @Operation(summary = "Obtener dirección por ID", description = "Devuelve una dirección específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección encontrada"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Direccion> obtenerDireccionPorId(@PathVariable Long id) {
        Optional<Direccion> direccion = direccionService.obtenerDireccionPorId(id);
        return direccion.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva dirección", description = "Registra una nueva dirección en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Dirección creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear la dirección")
    })
    @PostMapping
    public ResponseEntity<?> crearDireccion(@RequestBody Direccion direccion) {
        try {
            Direccion nueva = direccionService.crearDireccion(direccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar una dirección", description = "Actualiza una dirección existente usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró la dirección con el ID proporcionado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDireccion(@PathVariable Long id, @RequestBody Direccion datos) {
        try {
            Direccion actualizada = direccionService.actualizarDireccion(id, datos);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una dirección", description = "Elimina una dirección específica por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Dirección eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró la dirección para eliminar")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Long id) {
        try {
            direccionService.eliminarDireccionPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}