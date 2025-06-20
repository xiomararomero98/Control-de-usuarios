package com.example.Control_de_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Repository.RegionRepository;

@Configuration
@Order(1)
public class LoadDataBaseRegion {

    @Bean
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

}
