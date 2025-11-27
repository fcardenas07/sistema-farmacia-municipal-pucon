import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProductosStockTotalService } from '../../services/productos-stock-total.service';
import { ProductoFiltrado } from '../../models/producto-filtrado';
import { RespuestaPaginada } from '../../models/respuesta-paginada';

@Component({
  selector: 'app-total-productos-stock',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './total-productos-stock.component.html',
  styleUrls: ['./total-productos-stock.component.css']
})
export class TotalProductosStockComponent implements OnInit {

  productos: ProductoFiltrado[] = [];
  filteredList: ProductoFiltrado[] = [];

  paginaActual = 0;
  totalPaginas = 0;

  filters = {
    nombreComercial: '',
    nombreGenerico: ''
  };

  constructor(private stockService: ProductosStockTotalService) {}

  ngOnInit() {
    this.buscarProductos();
  }

  buscarProductos(pagina: number = 0) {
    this.paginaActual = pagina;

    this.stockService.buscarStockTotal(
      pagina,
      this.filters.nombreComercial,
      this.filters.nombreGenerico
    )
    .subscribe({
      next: (resp: RespuestaPaginada<ProductoFiltrado>) => {
        this.productos = resp.content;
        this.filteredList = resp.content;
        this.totalPaginas = resp.totalPages;
      },
      error: err => {
        console.error(err);
        alert("Error obteniendo productos.");
      }
    });
  }

  limpiarFiltros() {
    this.filters = {
      nombreComercial: '',
      nombreGenerico: ''
    };
    this.buscarProductos(0);
  }

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

  getStatusClass(estado: string): string {
    switch (estado) {
      case 'MUY_BAJO':
        return 'tag-low';
      case 'BAJO':
        return 'tag-medium';
      case 'MEDIO':
        return 'tag-medium';
      default:
        return 'tag-normal';
    }
  }

  // OPCIONES DINÁMICAS (de los dropdowns)
  get nombresComerciales(): string[] {
    return [...new Set(this.productos.map(p => p.nombreComercial).filter(x => x))];
  }

  get nombresGenericos(): string[] {
    return [...new Set(
      this.productos
        .filter(p => this.filters.nombreComercial ? p.nombreComercial === this.filters.nombreComercial : true)
        .map(p => p.nombreGenerico)
        .filter(x => x)
    )];
  }

}
