package com.example.Control_de_Usuarios.Controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @Operation(summary = "Obtener todas las comunas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de comunas obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay comunas disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Comuna>> obtenerComunas() {
        List<Comuna> comunas = comunaService.obtenerTodasLasComunas();
        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comunas);
    }

    @Operation(summary = "Obtener comuna por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna encontrada"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Comuna> obtenerComunaPorId(@PathVariable Long id) {
        Optional<Comuna> comuna = comunaService.obtenerComunaPorId(id);
        return comuna.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva comuna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comuna creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o región no encontrada")
    })
    @PostMapping
    public ResponseEntity<?> crearComuna(@RequestBody Comuna comuna) {
        try {
            Long idRegion = comuna.getRegion().getId();
            Comuna nueva = comunaService.crearComuna(comuna.getNombre(), idRegion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar una comuna existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarComuna(@PathVariable Long id, @RequestBody Comuna datos) {
        try {
            Comuna actualizada = comunaService.actualizarComuna(id, datos);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una comuna por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Comuna eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComuna(@PathVariable Long id) {
        try {
            comunaService.eliminarComunaPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}