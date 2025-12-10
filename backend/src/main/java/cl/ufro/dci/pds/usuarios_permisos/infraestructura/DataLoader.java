package cl.ufro.dci.pds.usuarios_permisos.infraestructura;


import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Rol;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setIdUsuario("1");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombreCompleto("Administrador del Sistema");
            admin.setEmail("admin@farmacia.cl");
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario ADMIN creado");
        }

        if (usuarioRepository.findByUsername("bodega").isEmpty()) {
            Usuario bodega = new Usuario();
            bodega.setIdUsuario("2");
            bodega.setUsername("bodega");
            bodega.setPassword(passwordEncoder.encode("bodega123"));
            bodega.setNombreCompleto("Personal de Bodega");
            bodega.setEmail("bodega@farmacia.cl");
            bodega.setRol(Rol.BODEGUERO);
            usuarioRepository.save(bodega);
            System.out.println("✅ Usuario BODEGA creado");
        }

        if (usuarioRepository.findByUsername("qf").isEmpty()) {
            Usuario qf = new Usuario();
            qf.setIdUsuario("3");
            qf.setUsername("qf");
            qf.setPassword(passwordEncoder.encode("qf123"));
            qf.setNombreCompleto("Químico Farmacéutico");
            qf.setEmail("qf@farmacia.cl");
            qf.setRol(Rol.QF);
            usuarioRepository.save(qf);
            System.out.println("✅ Usuario QF creado");
        }

        if (usuarioRepository.findByUsername("vendedor").isEmpty()) {
            Usuario vendedor = new Usuario();
            vendedor.setIdUsuario("4");
            vendedor.setUsername("vendedor");
            vendedor.setPassword(passwordEncoder.encode("vendedor123"));
            vendedor.setNombreCompleto("Vendedor");
            vendedor.setEmail("vendedor@farmacia.cl");
            vendedor.setRol(Rol.VENDEDOR);
            usuarioRepository.save(vendedor);
            System.out.println("✅ Usuario vendedor creado");
        }
    }
}
