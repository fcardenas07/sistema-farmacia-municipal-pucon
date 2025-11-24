package cl.ufro.dci.pds.infraestructura;

public class ImagenAlmacenadaException extends RuntimeException {

    public ImagenAlmacenadaException() {
        super("Error al guardar la imagen");
    }

    public ImagenAlmacenadaException(Throwable causa) {
        super("Error al guardar la imagen", causa);
    }
}