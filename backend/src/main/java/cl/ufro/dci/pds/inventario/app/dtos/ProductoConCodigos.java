package cl.ufro.dci.pds.inventario.app.dtos;

import java.util.List;

public interface ProductoConCodigos {
    List<? extends CodigoConCodigoBarra> codigos();
}