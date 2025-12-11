package cl.ufro.dci.pds.usuarios_permisos.app.controladores;


import cl.ufro.dci.pds.usuarios_permisos.app.dtos.AuthResponse;
import cl.ufro.dci.pds.usuarios_permisos.app.dtos.LoginRequest;
import cl.ufro.dci.pds.usuarios_permisos.app.dtos.UsuarioACrearRequest;
import cl.ufro.dci.pds.usuarios_permisos.app.dtos.UsuarioCreadoResponse;
import cl.ufro.dci.pds.usuarios_permisos.app.servicios.JwtService;
import cl.ufro.dci.pds.usuarios_permisos.app.servicios.ServicioUsuario;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.UsuarioDuplicadoException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ServicioUsuario servicioUsuario;


    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService, ServicioUsuario servicioUsuario) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.servicioUsuario = servicioUsuario;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String jwt = jwtService.generateToken(usuario);

        var response = new AuthResponse(
                jwt,
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getRol()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioCreadoResponse> crearUsuario(@Valid @RequestBody UsuarioACrearRequest dto) {
        UsuarioCreadoResponse creado = servicioUsuario.crearUsuario(dto);

        return ResponseEntity
                .created(URI.create("/usuarios/" + creado.idUsuario()))
                .body(creado);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> manejarCredencialesInvalidas(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales Inválidas");
    }

    @ExceptionHandler(UsuarioDuplicadoException.class)
    public ResponseEntity<String> manejarUsuarioDuplicado(UsuarioDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
