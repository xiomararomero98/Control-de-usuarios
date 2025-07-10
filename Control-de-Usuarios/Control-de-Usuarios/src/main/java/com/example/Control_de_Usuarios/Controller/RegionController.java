package com.example.Control_de_Usuarios.Controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Operation(summary = "Obtener todas las regiones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de regiones obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay regiones disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Region>> obtenerRegiones(){
        List<Region> regiones = regionService.obtenerRegiones();
        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regiones);
    }

    @Operation(summary = "Obtener una región por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región encontrada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Region> obtenerRegionPorId(@PathVariable Long id){
        Optional<Region> region = regionService.obtenerRegionPorId(id);
        return region.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva región",
        requestBody = @RequestBody(
            description = "Datos de la región a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Region.class),
                examples = @ExampleObject(value = "{\n  \"nombre\": \"Región del Maule\" \n}")
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Región creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Region> crearRegion(@RequestBody Region region){
        Region nueva = regionService.crearRegion(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar una región existente por ID",
        requestBody = @RequestBody(
            description = "Datos nuevos para actualizar la región",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Region.class),
                examples = @ExampleObject(value = "{\n  \"nombre\": \"Región del Biobío\" \n}")
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRegion(@PathVariable Long id, @RequestBody Region regionActualizada){
        try {
            Region actualizada = regionService.actualizarRegion(id, regionActualizada);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una región por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Región eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRegion(@PathVariable Long id){
        try {
            regionService.eliminarRegionPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}