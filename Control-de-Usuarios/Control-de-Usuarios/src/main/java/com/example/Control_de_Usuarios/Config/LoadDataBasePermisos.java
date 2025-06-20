package com.example.Control_de_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.Control_de_Usuarios.Model.Permisos;
import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Repository.PermisosRepository;
import com.example.Control_de_Usuarios.Repository.PrivilegiosRepository;
import com.example.Control_de_Usuarios.Repository.RolRepository;

@Configuration
@Order(6)
public class LoadDataBasePermisos {

    @Bean
    CommandLineRunner initPermisosDataBase(
            PermisosRepository permisosRepository,
            RolRepository rolRepository,
            PrivilegiosRepository privilegiosRepository) {

        return args -> {
            if (permisosRepository.count() == 0) {
                // Obtener roles y privilegios existentes
                Rol admin = rolRepository.findById(1L).orElse(null);
                Rol cliente = rolRepository.findById(5L).orElse(null);

                Privilegios visualizar = privilegiosRepository.findById(1L).orElse(null);
                Privilegios agregar = privilegiosRepository.findById(2L).orElse(null);

                if (admin != null && cliente != null && visualizar != null && agregar != null) {
                    permisosRepository.save(new Permisos(null, visualizar, admin));
                    permisosRepository.save(new Permisos(null, agregar, admin));
                    permisosRepository.save(new Permisos(null, visualizar, cliente));

                    System.out.println("Permisos precargados correctamente");
                } else {
                    System.out.println("Error: Faltan roles o privilegios necesarios para precargar permisos");
                }
            } else {
                System.out.println("Los permisos ya existen");
            }
        };
    }

}
