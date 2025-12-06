import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovimientosService } from '../../services/movimientos.service';
import { MovimientoDetalle } from '../../models/movimiento-detalle';
import { Router } from '@angular/router';

@Component({
  selector: 'app-detalle-movimiento-inventario-mermas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detalle-movimiento-inventario-mermas.component.html',
  styleUrl: './detalle-movimiento-inventario-mermas.component.css'
})
export class DetalleMovimientoInventarioMermasComponent implements OnInit {

  @Input() idMovimiento!: string;

  movimiento: MovimientoDetalle | null = null;
  cargando = true;
  error = false;

  constructor(
    private movimientosService: MovimientosService,
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
    this.movimientosService.getMovimiento(id).subscribe({
      next: (resp) => {
        this.movimiento = resp;
        this.cargando = false;
      },
      error: (err) => {
        console.error("❌ Error cargando movimiento:", err);
        this.error = true;
        this.cargando = false;
      }
    });
  }

  volver() {
    this.router.navigate(['/mermas-inventario']);
  }
}
