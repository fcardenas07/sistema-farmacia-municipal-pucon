package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.infraestructura.ServicioAlmacenamientoImagen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServicioAlmacenamientoImagenTest {

    @Autowired
    private ServicioAlmacenamientoImagen servicioAlmacenamientoImagen;

    private final Path carpetaTest = Path.of("src/test/resources/assets-test/productos");

    @BeforeEach
    void setUp() throws IOException {
        if (Files.exists(carpetaTest)) {
            Files.walk(carpetaTest)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(f -> {
                        if (!f.isDirectory()) f.delete();
                    });
        }
        Files.createDirectories(carpetaTest);
    }

    @AfterEach
    void limpiar() throws IOException {
        if (Files.exists(carpetaTest)) {
            Files.walk(carpetaTest)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(f -> {
                        if (!f.isDirectory()) f.delete();
                    });
        }
    }

    @Test
    @DisplayName("Guardar foto crea archivo en la carpeta de test")
    void guardarFoto_creaArchivo() throws IOException {
        var foto = new MockMultipartFile(
                "foto",
                "prueba.jpg",
                "image/jpeg",
                "contenido".getBytes()
        );

        String url = servicioAlmacenamientoImagen.guardarFoto(foto, "productos", "P");

        assertThat(url).isNotNull();
        assertThat(Files.exists(carpetaTest.resolve("P0001.jpg"))).isTrue();
    }

    @Test
    @DisplayName("Guardar varias fotos incrementa el nombre")
    void guardarVariasFotos_incrementaNombre() throws IOException {
        var foto1 = new MockMultipartFile("foto", "1.jpg", "image/jpeg", "a".getBytes());
        var foto2 = new MockMultipartFile("foto", "2.jpg", "image/jpeg", "b".getBytes());

        String url1 = servicioAlmacenamientoImagen.guardarFoto(foto1, "productos", "P");
        String url2 = servicioAlmacenamientoImagen.guardarFoto(foto2, "productos", "P");

        assertThat(url1).isEqualTo("productos/P0001.jpg");
        assertThat(url2).isEqualTo("productos/P0002.jpg");
        assertThat(Files.exists(carpetaTest.resolve("P0002.jpg"))).isTrue();
    }

    @Test
    @DisplayName("Si el archivo es nulo o vacío devuelve null")
    void guardarFoto_nuloOVacio() {
        assertThat(servicioAlmacenamientoImagen.guardarFoto(null, "productos", "P")).isNull();

        var vacio = new MockMultipartFile("foto", "vacio.jpg", "image/jpeg", new byte[0]);
        assertThat(servicioAlmacenamientoImagen.guardarFoto(vacio, "productos", "P")).isNull();
    }
}
