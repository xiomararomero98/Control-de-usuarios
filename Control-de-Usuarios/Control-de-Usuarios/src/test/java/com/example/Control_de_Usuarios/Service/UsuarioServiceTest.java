package com.example.Control_de_Usuarios.Service;

import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Repository.RolRepository;
import com.example.Control_de_Usuarios.Repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Rol rol;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        rol = new Rol(1L, "CLIENTE");
        usuario = new Usuario(1L, "Carlos", "Soto", "carlos@test.com", "clave123", new Date(), rol, new ArrayList<>());
    }

    @Test
    void obtenerUsuarios_devuelveListaUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.obtenerUsuarios();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCorreo()).isEqualTo("carlos@test.com");
    }

    @Test
    void obtenerUsuarioPorId_devuelveUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Carlos");
    }

    @Test
    void findByCorreo_devuelveUsuario() {
        when(usuarioRepository.findByCorreo("carlos@test.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findByCorreo("carlos@test.com");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getApellido()).isEqualTo("Soto");
    }

    @Test
    void crearUsuario_conDatosValidos_devuelveUsuarioCreado() {
        when(usuarioRepository.findByCorreo("nuevo@test.com")).thenReturn(Optional.empty());
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(2L);
            return u;
        });

        Usuario creado = usuarioService.crearUsuario("Nuevo", "Usuario", "nuevo@test.com", "clave123", 1L);

        assertThat(creado.getId()).isEqualTo(2L);
        assertThat(creado.getNombre()).isEqualTo("Nuevo");
        assertThat(creado.getRol().getNombre()).isEqualTo("CLIENTE");
    }

    @Test
    void actualizarUsuario_actualizaYDevuelveUsuario() {
        Usuario actualizado = new Usuario(null, "Mario", "Gomez", "mario@test.com", "nuevaclave", new Date(), rol, new ArrayList<>());
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        Optional<Usuario> resultado = usuarioService.actualizarUsuario(1L, actualizado);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Mario");
        assertThat(resultado.get().getCorreo()).isEqualTo("mario@test.com");
    }

    @Test
    void eliminarUsuario_invocaDeleteById() {
        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}