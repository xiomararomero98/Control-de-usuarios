package com.example.Control_de_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Repository.PrivilegiosRepository;

@Configuration
@Order(4)
public class LoadDataBasePrivilegios {

    @Bean
     CommandLineRunner initPrivilegios(PrivilegiosRepository privilegiosRepository) {
        return args -> {
            if (privilegiosRepository.count() == 0) {
                privilegiosRepository.save(new Privilegios(null, "Visualizar perfumes", null));
                privilegiosRepository.save(new Privilegios(null, "Agregar productos", null));
                privilegiosRepository.save(new Privilegios(null, "Gestionar usuarios", null));
                System.out.println(" Privilegios precargados");
            } else {
                System.out.println("Los privilegios ya existen");
            }
        };
    }

}
