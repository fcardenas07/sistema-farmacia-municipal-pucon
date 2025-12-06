import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { IngresosService } from '../../services/ingresos.service';
import { IngresoLote } from '../../models/ingreso-lote';

@Component({
  selector: 'app-inventario-lotes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventario-lotes.component.html',
  styleUrls: ['./inventario-lotes.component.css']
})
export class InventarioLotesComponent implements OnInit {

  lotes: IngresoLote[] = [];
  paginaActual: number = 0;
  totalPaginas: number = 0;

  constructor(
    private ingresosService: IngresosService,
    private router: Router   // ✔ AHORA SI
  ) {}

  ngOnInit() {
    this.cargarPagina(0);
  }

  cargarPagina(page: number) {
    this.ingresosService.getIngresos(page).subscribe({
      next: (data) => {
        this.lotes = data.content;
        this.paginaActual = data.number;
        this.totalPaginas = data.totalPages;
      },
      error: (err) => console.error('Error cargando ingresos:', err)
    });
  }

  paginaAnterior() {
    if (this.paginaActual > 0) {
      this.cargarPagina(this.paginaActual - 1);
    }
  }

  paginaSiguiente() {
    if (this.paginaActual < this.totalPaginas - 1) {
      this.cargarPagina(this.paginaActual + 1);
    }
  }

  verDetalles(lote: IngresoLote) {
    this.router.navigate(['/detalle-movimiento', lote.idMovimiento]);  // ✔ AHORA FUNCIONA
  }

}
