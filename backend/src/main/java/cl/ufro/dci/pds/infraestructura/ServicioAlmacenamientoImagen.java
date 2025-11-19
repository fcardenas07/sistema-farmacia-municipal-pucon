package cl.ufro.dci.pds.infraestructura;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ServicioAlmacenamientoImagen {

    private final Path baseDirectory;

    public ServicioAlmacenamientoImagen(@Value("${imagenes.base-dir}") String baseDir) {
        this.baseDirectory = Path.of(baseDir);
    }

    /**
     * Guarda la foto en la subcarpeta indicada con un nombre incremental y devuelve la URL relativa.
     *
     * @param foto       archivo a guardar
     * @param subcarpeta subcarpeta dentro de assets (productos, proveedores, etc.)
     * @param prefijo    prefijo del archivo (ej: "P" para productos, "PR" para proveedores)
     * @return URL relativa de la imagen guardada
     */
    public String guardarFoto(MultipartFile foto, String subcarpeta, String prefijo) {
        if (foto == null || foto.isEmpty()) return null;

        try {
            var carpetaDestino = Path.of(String.valueOf(baseDirectory), subcarpeta);
            Files.createDirectories(carpetaDestino);

            var extension = extraerExtension(foto.getOriginalFilename());
            var nombreArchivo = generarNombreIncremental(carpetaDestino, prefijo, extension);

            var archivoDestino = carpetaDestino.resolve(nombreArchivo);
            Files.copy(foto.getInputStream(), archivoDestino, StandardCopyOption.REPLACE_EXISTING);

            return subcarpeta + "/" + nombreArchivo;
        } catch (IOException e) {
            throw new ImagenAlmacenadaException(e);
        }
    }

    private synchronized String generarNombreIncremental(Path carpetaDestino, String prefijo, String extension) throws IOException {
        int max;
        try (var stream = Files.list(carpetaDestino)) {
            max = stream
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(n -> n.startsWith(prefijo))
                    .map(n -> n.replaceAll("\\D", ""))
                    .mapToInt(s -> {
                        try { return Integer.parseInt(s); }
                        catch (NumberFormatException e) { return 0; }
                    })
                    .max()
                    .orElse(0);
        }

        var siguiente = max + 1;
        return String.format("%s%04d.%s", prefijo, siguiente, extension);
    }

    private String extraerExtension(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) return "jpg";
        return nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1).toLowerCase();
    }
}
