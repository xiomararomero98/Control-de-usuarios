package com.example.Control_de_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Direccion;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Repository.ComunaRepository;
import com.example.Control_de_Usuarios.Repository.DireccionRepository;
import com.example.Control_de_Usuarios.Repository.UsuarioRepository;

@Configuration
@Order(7)
public class LoadDataBaseDireccion {


    @Bean
    CommandLineRunner initDirecciones(
        DireccionRepository direccionRepository,
        ComunaRepository comunaRepository,
        UsuarioRepository usuarioRepository
    ) {
        return args -> {
            if (direccionRepository.count() == 0) {
                Usuario benjamin = usuarioRepository.findById(1L).orElse(null);
                Usuario laura = usuarioRepository.findById(2L).orElse(null);
                Comuna comuna = comunaRepository.findById(1L).orElse(null);

                if (benjamin != null && laura != null && comuna != null) {
                    direccionRepository.save(new Direccion(null, "Av. Providencia", 1234, null, null, benjamin, comuna));
                    direccionRepository.save(new Direccion(null, "Pasaje Las Palmas", 789, 5, "A", laura, comuna));
                    System.out.println("Direcciones precargadas correctamente para Benjamín y Laura");
                } else {
                    System.out.println("No se encontraron usuarios o comuna para precargar direcciones");
                }
            } else {
                System.out.println("Las direcciones ya existen");
            }
        };
    }
}
