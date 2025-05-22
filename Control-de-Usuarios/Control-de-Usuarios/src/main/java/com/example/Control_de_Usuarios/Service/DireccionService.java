package com.example.Control_de_Usuarios.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Direccion;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Repository.ComunaRepository;
import com.example.Control_de_Usuarios.Repository.DireccionRepository;
import com.example.Control_de_Usuarios.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunaRepository comunaRepository;


    //obtener todas las direcciones
    public List <Direccion> obtenerTodasLasDirecciones(){
        return direccionRepository.findAll();
    }

    //obtener direccion por ID
    public Optional<Direccion> obtenerDireccionPorId(Long id){
        return direccionRepository.findById(id);
    }

    //crear una nueva direccion

    public Direccion crearDireccion(Direccion direccion) {
    Long idUsuario = direccion.getUsuario().getId();
    Long idComuna = direccion.getComuna().getId();

    Usuario usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

    Comuna comuna = comunaRepository.findById(idComuna)
        .orElseThrow(() -> new RuntimeException("Comuna no encontrada con ID: " + idComuna));

    direccion.setUsuario(usuario);
    direccion.setComuna(comuna);

    return direccionRepository.save(direccion);
}

    // actualizar una direccion 
    public Direccion actualizarDireccion(Long id, Direccion datos){
        Direccion existente= direccionRepository.findById(id)
        .orElseThrow(() ->new RuntimeException("Direccion no encontrada"));

        existente.setCalle(datos.getCalle());
        existente.setNumeracion(datos.getNumeracion());
        existente.setNumeroDepartamento(datos.getNumeroDepartamento());
        existente.setTorre(datos.getTorre());
        existente.setUsuario(datos.getUsuario());

        return direccionRepository.save(existente);
    }

    //eliminar una direccion por id

    public void eliminarDireccionPorId (Long id){
    if (!direccionRepository.existsById(id)) {
        throw new RuntimeException("Direccion no encontrada con ID:"+id);
        }
        direccionRepository.deleteById(id);
    }


}
