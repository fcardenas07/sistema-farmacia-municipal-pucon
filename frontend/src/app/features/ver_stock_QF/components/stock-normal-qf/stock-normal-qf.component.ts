import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProductoFiltradoQF } from '../../models/producto-filtrado-qf';
import { RespuestaPaginadaQf } from '../../models/respuesta-paginada-qf';
import { StockNormalService } from '../../services/stock-normal.service';

@Component({
  selector: 'app-stock-normal-qf',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './stock-normal-qf.component.html',
  styleUrls: ['./stock-normal-qf.component.css']
})
export class StockNormalQfComponent implements OnInit {

  productos: ProductoFiltradoQF[] = [];
  filteredList: ProductoFiltradoQF[] = [];

  paginaActual = 0;
  totalPaginas = 0;

  filters = {
    nombreComercial: '',
    nombreGenerico: ''
  };

  constructor(private stockService: StockNormalService) {}

  ngOnInit() {
    this.buscarProductos();
  }

  /* ============================================================
     🔍 LLAMADA AL BACKEND QF
     ============================================================ */
  buscarProductos(pagina: number = 0) {
    this.paginaActual = pagina;

    this.stockService.buscarStockTotal(
      pagina,
      this.filters.nombreComercial,
      this.filters.nombreGenerico
    )
    .subscribe({
      next: (resp: RespuestaPaginadaQf<ProductoFiltradoQF>) => {
        this.productos = resp.content;
        this.filteredList = resp.content;
        this.totalPaginas = resp.totalPages;
      },
      error: err => {
        console.error(err);
        alert("Error obteniendo productos de stock normal QF.");
      }
    });
  }

  /* ============================================================
     🔄 LIMPIAR FILTROS
     ============================================================ */
  limpiarFiltros() {
    this.filters = {
      nombreComercial: '',
      nombreGenerico: ''
    };
    this.buscarProductos(0);
  }

  /* ============================================================
     ⏪⏩ PAGINACIÓN
     ============================================================ */
  paginaAnterior() {
    if (this.paginaActual > 0) {
      this.buscarProductos(this.paginaActual - 1);
    }
  }

  paginaSiguiente() {
    if (this.paginaActual + 1 < this.totalPaginas) {
      this.buscarProductos(this.paginaActual + 1);
    }
  }

  /* ============================================================
     🎨 TAGS DE COLOR
     ============================================================ */
  getStatusClass(estado: string): string {
    switch (estado) {
      case 'MUY_BAJO':
        return 'tag-low';
      case 'BAJO':
      case 'MEDIO':
        return 'tag-medium';
      default:
        return 'tag-normal';
    }
  }

  /* ============================================================
     📌 DROPDOWNS DINÁMICOS
     ============================================================ */
  get nombresComerciales(): string[] {
    return [...new Set(this.productos.map(p => p.nombreComercial).filter(x => x))];
  }

  get nombresGenericos(): string[] {
    return [
      ...new Set(
        this.productos
          .filter(p =>
            this.filters.nombreComercial
              ? p.nombreComercial === this.filters.nombreComercial
              : true
          )
          .map(p => p.nombreGenerico)
          .filter(x => x)
      )
    ];
  }

}
