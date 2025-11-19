package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.Imagen;
import org.springframework.web.multipart.MultipartFile;

public record FotoProductoASubir(
        @Imagen
        MultipartFile foto
) { }
