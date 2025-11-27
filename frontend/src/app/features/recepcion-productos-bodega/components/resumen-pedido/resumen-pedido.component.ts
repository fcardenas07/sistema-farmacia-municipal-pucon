import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LotesService } from '../../services/lotes.service';
import { ProductosBodegaService } from '../../services/productos-bodega.service';
import { LoteInfo } from '../../models/lote-info';

@Component({
  selector: 'app-resumen-pedido',
  standalone: true,
  templateUrl: './resumen-pedido.component.html',
  styleUrls: ['./resumen-pedido.component.css']
})
export class ResumenPedidoComponent implements OnInit {

  lotes: LoteInfo[] = [];

  constructor(
    private lotesService: LotesService,
    private productosService: ProductosBodegaService,
    private router: Router
  ) {}

  ngOnInit() {
    this.lotes = this.lotesService.getLotes();
    console.log("📦 Lotes recibidos en resumen:", this.lotes);
  }

  // -------------------------------
  // VOLVER A ESCANEAR PRODUCTOS
  // -------------------------------
  volver() {
    this.router.navigate(['/agregar-stock-pedido']);
  }

  // -------------------------------
  // ELIMINAR LOTE
  // -------------------------------
  eliminar(index: number) {
    this.lotes.splice(index, 1);
    this.lotesService.setLotes(this.lotes);
  }

  // -------------------------------
  // FINALIZAR PEDIDO (ENVIAR POST)
  // -------------------------------
  finalizarPedido() {
    if (this.lotes.length === 0) {
      alert("No hay lotes para enviar.");
      return;
    }

    console.log("🚀 Enviando pedido completo...");

    this.lotes.forEach((lote, index) => {
      const payload = {
        fechaElaboracion: lote.fechaElaboracion,
        fechaVencimiento: lote.fechaVencimiento,
        estado: "ACTIVO",
        numeroLote: lote.numeroLote,
        cantidad: lote.cantidad,
        limiteMerma: lote.limiteMerma,
        porcentajeOferta: null,
        precioUnitario: lote.precioUnitario,
        idGuiaIngreso: null,
        codigo: {
          idProducto: lote.product.idProducto!,
          codigoBarra: lote.codigoBarra,
          tipoCodigo: "EAN",
          activo: true
        }
      };

      console.log("📤 Enviando lote:", payload);

      this.productosService.postInventario(payload).subscribe({
        next: (resp) => {
          console.log(`✔ Lote ${index + 1} enviado correctamente`, resp);

          // Si es el último lote => limpiar memoria y volver al inicio
          if (index === this.lotes.length - 1) {
            this.lotesService.clear();
            alert("Pedido enviado correctamente.");
            this.router.navigate(['/agregar-stock-pedido']);
          }
        },
        error: (err) => {
          console.error(`❌ Error enviando lote ${index + 1}`, err);
          alert("Error enviando el pedido, revisa consola.");
        }
      });
    });
  }
}
