package com.example.Control_de_Usuarios.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Service.PrivilegiosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/privilegios")
public class PrivilegiosController {

    @Autowired
    private PrivilegiosService privilegiosService;

    @Operation(summary = "Obtener todos los privilegios", description = "Retorna una lista de todos los privilegios registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de privilegios obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay privilegios registrados")
    })
    @GetMapping
    public ResponseEntity<List<Privilegios>> obtenerPrivilegios() {
        List<Privilegios> lista = privilegiosService.obtenerPrivilegios();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener privilegio por ID", description = "Retorna un privilegio según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Privilegio encontrado"),
        @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Privilegios> obtenerPrivilegioPorId(@PathVariable Long id) {
        Optional<Privilegios> privilegio = privilegiosService.obtenerPrivilegioPorId(id);
        if (privilegio.isPresent()) {
            return ResponseEntity.ok(privilegio.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo privilegio", description = "Permite crear un nuevo privilegio.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Privilegio creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear el privilegio")
    })
    @PostMapping
    public ResponseEntity<Privilegios> crearPrivilegios(@RequestBody Privilegios privilegio) {
        Privilegios nuevo = privilegiosService.creaPrivilegio(privilegio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar un privilegio", description = "Permite actualizar un privilegio existente por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Privilegio actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPrivilegios(@PathVariable Long id, @RequestBody Privilegios privilegioActualizado) {
        try {
            Privilegios actualizado = privilegiosService.actualizarPrivilegio(id, privilegioActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un privilegio", description = "Elimina un privilegio según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Privilegio eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrivilegio(@PathVariable Long id) {
        privilegiosService.eliminarPrivilegio(id);
        return ResponseEntity.noContent().build();
    }
}