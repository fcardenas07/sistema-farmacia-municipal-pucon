package cl.ufro.dci.pds.infraestructura.email;

public interface EmailService {
    void enviar(String destinatario, String asunto, String htmlContenido);
}
