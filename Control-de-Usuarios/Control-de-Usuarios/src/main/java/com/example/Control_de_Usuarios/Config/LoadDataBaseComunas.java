package com.example.Control_de_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Repository.ComunaRepository;
import com.example.Control_de_Usuarios.Repository.RegionRepository;

@Configuration
@Order(4)
public class LoadDataBaseComunas {
    @Bean
    CommandLineRunner initComunaDataBase(ComunaRepository comunaRepository, RegionRepository regionRepository) {
        return args -> {
            if (comunaRepository.count() == 0) {

                cargarComunas(regionRepository, comunaRepository, "Región de Arica y Parinacota", "Arica", "Putre", "Camarones");
                cargarComunas(regionRepository, comunaRepository, "Región de Tarapacá", "Iquique", "Alto Hospicio", "Pozo Almonte");
                cargarComunas(regionRepository, comunaRepository, "Región de Antofagasta", "Antofagasta", "Calama", "Tocopilla");
                cargarComunas(regionRepository, comunaRepository, "Región de Atacama", "Copiapó", "Vallenar", "Caldera");
                cargarComunas(regionRepository, comunaRepository, "Región de Coquimbo", "La Serena", "Coquimbo", "Ovalle");
                cargarComunas(regionRepository, comunaRepository, "Región de Valparaíso", "Valparaíso", "Viña del Mar", "Quillota");
                cargarComunas(regionRepository, comunaRepository, "Región Metropolitana de Santiago", "Santiago", "Puente Alto", "Maipú");
                cargarComunas(regionRepository, comunaRepository, "Región del Libertador General Bernardo O'Higgins", "Rancagua", "San Fernando", "Santa Cruz");
                cargarComunas(regionRepository, comunaRepository, "Región del Maule", "Talca", "Curicó", "Linares");
                cargarComunas(regionRepository, comunaRepository, "Región de Ñuble", "Chillán", "San Carlos", "Bulnes");
                cargarComunas(regionRepository, comunaRepository, "Región del Biobío", "Concepción", "Los Ángeles", "Coronel");
                cargarComunas(regionRepository, comunaRepository, "Región de La Araucanía", "Temuco", "Villarrica", "Angol");
                cargarComunas(regionRepository, comunaRepository, "Región de Los Ríos", "Valdivia", "La Unión", "Panguipulli");
                cargarComunas(regionRepository, comunaRepository, "Región de Los Lagos", "Puerto Montt", "Castro", "Osorno");
                cargarComunas(regionRepository, comunaRepository, "Región de Aysén del General Carlos Ibáñez del Campo", "Coyhaique", "Puerto Aysén", "Chile Chico");
                cargarComunas(regionRepository, comunaRepository, "Región de Magallanes y de la Antártica Chilena", "Punta Arenas", "Puerto Natales", "Porvenir");

                System.out.println("Comunas precargadas por región.");
            } else {
                System.out.println("Las comunas ya existen.");
            }
        };
    }

    private void cargarComunas(RegionRepository regionRepo, ComunaRepository comunaRepo, String nombreRegion, String... comunas) {
        Region region = regionRepo.findByNombre(nombreRegion);
        if (region != null) {
            for (String nombreComuna : comunas) {
                comunaRepo.save(new Comuna(null, nombreComuna, region, null));
            }
        } else {
            System.out.println("Región no encontrada: " + nombreRegion);
        }
    }

}
