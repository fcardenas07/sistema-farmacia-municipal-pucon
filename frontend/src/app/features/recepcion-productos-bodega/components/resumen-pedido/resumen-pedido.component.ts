import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LotesService } from '../../services/lotes.service';
import { ProductosBodegaService } from '../../services/productos-bodega.service';
import { LoteInfo } from '../../models/lote-info';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-resumen-pedido',
  standalone: true,
  imports: [CommonModule],
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

  // VOLVER A ESCANEAR
  volver() {
    this.router.navigate(['/agregar-stock-pedido']);
  }

  // ELIMINAR LOTE
  eliminar(index: number) {
    this.lotes.splice(index, 1);
    this.lotesService.setLotes(this.lotes);
  }

  // FINALIZAR PEDIDO
  finalizarPedido() {
    if (this.lotes.length === 0) {
      alert("No hay lotes para enviar.");
      return;
    }

    console.log("🚀 Enviando pedido completo...");

    this.lotes.forEach((lote, index) => {

      console.log("📤 Preparando envío de lote:", lote);

      // ✔ Usa el método que ya crea el payload correcto
      this.productosService.postInventarioFromLote(lote).subscribe({
        next: (resp) => {
          console.log(`✔ Lote ${index + 1} enviado correctamente`, resp);

          // Si es el último lote
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
