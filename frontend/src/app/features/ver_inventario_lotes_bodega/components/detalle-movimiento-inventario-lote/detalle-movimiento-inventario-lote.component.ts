import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovimientosService } from '../../services/movimientos.service';
import { MovimientoDetalle } from '../../models/movimiento-detalle';
import { Router } from '@angular/router';

@Component({
  selector: 'app-detalle-movimiento-inventario-lote',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detalle-movimiento-inventario-lote.component.html',
  styleUrls: ['./detalle-movimiento-inventario-lote.component.css']
})
export class DetalleMovimientoInventarioLoteComponent implements OnInit {

  @Input() idMovimiento!: string;

  movimiento: MovimientoDetalle | null = null;
  cargando = true;
  error = false;

  constructor(
    private movimientoService: MovimientosService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.idMovimiento) {
      console.error("❌ No se recibió idMovimiento como @Input()");
      this.error = true;
      this.cargando = false;
      return;
    }

    this.cargarMovimiento(this.idMovimiento);
  }

  cargarMovimiento(id: string) {
    this.movimientoService.getMovimiento(id).subscribe({
      next: data => {
        this.movimiento = data;
        this.cargando = false;
      },
      error: err => {
        console.error('❌ Error cargando movimiento:', err);
        this.error = true;
        this.cargando = false;
      }
    });
  }

  // 🔥 MÉTODO NECESARIO PARA QUE EL HTML NO FALLE
  volver() {
    this.router.navigate(['/inventario-lotes-bodega']);
  }

}
