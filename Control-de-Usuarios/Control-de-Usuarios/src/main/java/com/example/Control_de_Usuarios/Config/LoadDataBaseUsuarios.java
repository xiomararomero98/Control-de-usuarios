package com.example.Control_de_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Repository.RolRepository;
import com.example.Control_de_Usuarios.Repository.UsuarioRepository;
import com.example.Control_de_Usuarios.Service.Encriptador;

import java.util.Date;
import java.util.List;

@Configuration
@Order(5)
public class LoadDataBaseUsuarios {

    @Bean
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

}
