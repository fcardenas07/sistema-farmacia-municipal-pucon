import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MovimientosMermaService } from '../../services/movimientos-merma.service';
import { MovimientoMerma } from '../../models/movimiento-merma';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inventario-mermas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventario-mermas.component.html',
  styleUrl: './inventario-mermas.component.css'
})
export class InventarioMermasComponent implements OnInit {

  mermas: MovimientoMerma[] = [];
  paginaActual = 0;
  totalPaginas = 0;

  constructor(
    private mermaService: MovimientosMermaService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cargarPagina(0);
  }

  cargarPagina(page: number) {
    this.mermaService.getMermas(page).subscribe({
      next: data => {
        this.mermas = data.content;
        this.paginaActual = data.number;
        this.totalPaginas = data.totalPages;
      },
      error: err => console.error('Error cargando mermas:', err)
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

  verDetalles(merma: MovimientoMerma) {
    this.router.navigate(['/detalle-movimiento-merma', merma.idMovimiento]);

  }
}
