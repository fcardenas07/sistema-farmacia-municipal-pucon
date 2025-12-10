package cl.ufro.dci.pds.usuarios_permisos.app.servicios;

import cl.ufro.dci.pds.usuarios_permisos.app.dtos.UsuarioACrearRequest;
import cl.ufro.dci.pds.usuarios_permisos.app.dtos.UsuarioCreadoResponse;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.UsuarioDuplicadoException;
import cl.ufro.dci.pds.usuarios_permisos.infraestructura.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ServicioUsuario {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ServicioUsuario(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioCreadoResponse crearUsuario(UsuarioACrearRequest dto) {
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new UsuarioDuplicadoException("El username '" + dto.username() + "' ya está en uso");
        }

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new UsuarioDuplicadoException("El email '" + dto.email() + "' ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(UUID.randomUUID().toString());
        usuario.setUsername(dto.username());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setNombreCompleto(dto.nombreCompleto());
        usuario.setEmail(dto.email());
        usuario.setRol(dto.rol());
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return new UsuarioCreadoResponse(
                usuarioGuardado.getIdUsuario(),
                usuarioGuardado.getUsername(),
                usuarioGuardado.getNombreCompleto(),
                usuarioGuardado.getEmail(),
                usuarioGuardado.getRol(),
                usuarioGuardado.isActivo()
        );
    }
}