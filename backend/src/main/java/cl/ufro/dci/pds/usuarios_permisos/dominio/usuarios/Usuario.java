package cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @Column(name = "id_usuario")
    private String idUsuario;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "activo", nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

    public Usuario() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Métodos de Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return activo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return activo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return activo;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(idUsuario, usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUsuario);
    }

    @Override
    public String toString() {
        return "Usuario{" +
               "idUsuario='" + idUsuario + '\'' +
               ", username='" + username + '\'' +
               ", nombreCompleto='" + nombreCompleto + '\'' +
               ", email='" + email + '\'' +
               ", rol=" + rol +
               '}';
    }
}