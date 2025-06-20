package com.example.Control_de_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Repository.ComunaRepository;
import com.example.Control_de_Usuarios.Repository.DireccionRepository;
import com.example.Control_de_Usuarios.Repository.PermisosRepository;
import com.example.Control_de_Usuarios.Repository.PrivilegiosRepository;
import com.example.Control_de_Usuarios.Repository.RolRepository;

@Configuration
@Order(2)
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(
        RolRepository rolRepository,
        PrivilegiosRepository privilegioRepository,
        PermisosRepository permisoRepository,
        ComunaRepository comunaRepository,
        DireccionRepository direccionRepository){
        return args -> {
            if (rolRepository.count()==0) {
                rolRepository.save(new Rol(null, "Administrador"));
                rolRepository.save(new Rol(null, "Gerente de Sucursal"));
                rolRepository.save(new Rol(null, "Empleado de ventas"));
                rolRepository.save(new Rol(null, "Logistica"));
                rolRepository.save(new Rol(null, "Clientes"));
                System.out.println("Roles crreados correctamente");
                
            } else {
                System.out.println("Los roles ya existen. No se cargaron nuevos datos");
                
            }
            
        };
    }

}
