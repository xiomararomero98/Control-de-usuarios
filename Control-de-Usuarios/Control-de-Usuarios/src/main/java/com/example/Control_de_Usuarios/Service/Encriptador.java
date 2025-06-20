package com.example.Control_de_Usuarios.Service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;


@Component
public class Encriptador {
    // Encripta la contraseña usando BCrypt
    public static String encriptar(String clave) {
        return BCrypt.hashpw(clave, BCrypt.gensalt());
    }

    // Verifica si la clave ingresada coincide con la clave encriptada
    public static boolean verificar(String claveIngresada, String claveEncriptada) {
        return BCrypt.checkpw(claveIngresada, claveEncriptada);
    }

}
