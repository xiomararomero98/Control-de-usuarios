package com.example.Control_de_Usuarios.Config;

import com.example.Control_de_Usuarios.Model.*;
import com.example.Control_de_Usuarios.Repository.*;
import com.example.Control_de_Usuarios.Service.Encriptador;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.List;

@Configuration
public class LoadDataBase {

    @Bean
    @Order(1)
    CommandLineRunner initRegionDataBase(RegionRepository regionRepository){
    return args->{
        if (regionRepository.count()==0) {
            regionRepository.save(new Region(null,"Región de Arica y Parinacota", null));
            regionRepository.save(new Region(null, "Región de Tarapacá", null));
            regionRepository.save(new Region(null, "Región de Antofagasta", null));
            regionRepository.save(new Region(null, "Región de Atacama", null));
            regionRepository.save(new Region(null, "Región de Coquimbo", null));
            regionRepository.save(new Region(null, "Región de Valparaíso", null));
            regionRepository.save(new Region(null, "Región Metropolitana de Santiago", null));
            regionRepository.save(new Region(null, "Región del Libertador General Bernardo O'Higgins", null));
            regionRepository.save(new Region(null, "Región del Maule", null));
            regionRepository.save(new Region(null, "Región de Ñuble", null));
            regionRepository.save(new Region(null, "Región del Biobío", null));
            regionRepository.save(new Region(null, "Región de La Araucanía", null));
            regionRepository.save(new Region(null, "Región de Los Ríos", null));
            regionRepository.save(new Region(null, "Región de Los Lagos", null));
            regionRepository.save(new Region(null, "Región de Aysén del General Carlos Ibáñez del Campo", null));                regionRepository.save(new Region(null, "Región de Magallanes y de la Antártica Chilena", null));
            
        } else {
            System.out.println("las regiones ya existen");
        }

    };
}
    @Bean
    @Order(2)
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


    
    @Bean
    @Order(3)
    CommandLineRunner cargarRoles(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.count() == 0) {
                System.out.println("==> Cargando Roles");
                rolRepository.save(new Rol(null, "ADMINISTRADOR"));
                rolRepository.save(new Rol(null, "GERENTE"));
                rolRepository.save(new Rol(null, "EMPLEADO DE VENTAS"));
                rolRepository.save(new Rol(null, "LOGISTICA"));
                rolRepository.save(new Rol(null, "CLIENTE"));
            }
        };
    }

    @Bean
    @Order(4)
    CommandLineRunner cargarPrivilegios(PrivilegiosRepository privilegiosRepository) {
        return args -> {
            if (privilegiosRepository.count() == 0) {
                System.out.println("==> Cargando Privilegios");
                privilegiosRepository.save(new Privilegios(null, "Visualizar perfumes", null));
                privilegiosRepository.save(new Privilegios(null, "Agregar productos", null));
                privilegiosRepository.save(new Privilegios(null, "Gestionar usuarios", null));
            }
        };
    }

    @Bean
    @Order(5)
    CommandLineRunner cargarPermisos(PermisosRepository permisosRepository,
                                     RolRepository rolRepository,
                                     PrivilegiosRepository privilegiosRepository) {
        return args -> {
            if (permisosRepository.count() == 0) {
                System.out.println("==> Cargando Permisos");
                Rol admin = rolRepository.findById(1L).orElse(null);
                Rol cliente = rolRepository.findById(5L).orElse(null);
                Privilegios visualizar = privilegiosRepository.findById(1L).orElse(null);
                Privilegios agregar = privilegiosRepository.findById(2L).orElse(null);

                if (admin != null && cliente != null && visualizar != null && agregar != null) {
                    permisosRepository.save(new Permisos(null, visualizar, admin));
                    permisosRepository.save(new Permisos(null, agregar, admin));
                    permisosRepository.save(new Permisos(null, visualizar, cliente));
                } else {
                    System.out.println("Faltan roles o privilegios necesarios para precargar permisos");
                }
            }
        };
    }

    
    @Bean
    @Order(6)
    CommandLineRunner initUsuarios(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Rol admin = rolRepository.findById(1L).orElse(null); // Administrador
                Rol cliente = rolRepository.findById(5L).orElse(null); // Cliente

                if (admin != null && cliente != null) {
                    Usuario u1 = new Usuario(null, "Benjamín", "Zerán", "benja@admin.cl",
    Encriptador.encriptar("123456"), new Date(), admin, List.of());

Usuario u2 = new Usuario(null, "Laura", "Pérez", "laura@cliente.cl",
    Encriptador.encriptar("654321"), new Date(), cliente, List.of());

                    usuarioRepository.save(u1);
                    usuarioRepository.save(u2);

                    System.out.println("Usuarios precargados correctamente");
                } else {
                    System.out.println(" Faltan roles necesarios para crear usuarios");
                }
            } else {
                System.out.println("Ya existen usuarios precargados");
            }
        };
    }



    @Bean
    @Order(7)
    CommandLineRunner cargarDirecciones(DireccionRepository direccionRepository,
                                        UsuarioRepository usuarioRepository,
                                        ComunaRepository comunaRepository) {
        return args -> {
            if (direccionRepository.count() == 0) {
                System.out.println("==> Cargando Direcciones");
                Usuario benjamin = usuarioRepository.findById(1L).orElse(null);
                Usuario laura = usuarioRepository.findById(2L).orElse(null);
                Comuna comuna = comunaRepository.findById(1L).orElse(null);

                if (benjamin != null && laura != null && comuna != null) {
                    direccionRepository.save(new Direccion(null, "Av. Providencia", 1234, null, null, benjamin, comuna));
                    direccionRepository.save(new Direccion(null, "Pasaje Las Palmas", 789, 5, "A", laura, comuna));
                } else {
                    System.out.println("No se encontraron usuarios o comuna para precargar direcciones");
                }
            }
        };
    }
}
