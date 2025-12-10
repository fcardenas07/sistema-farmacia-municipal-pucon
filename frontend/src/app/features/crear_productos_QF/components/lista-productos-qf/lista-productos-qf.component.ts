import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProductosStockTotalService } from '../../../ver_stock_bodega/services/productos-stock-total.service';
import { ProductoFiltrado } from '../../../ver_stock_bodega/models/producto-filtrado';
import { RespuestaPaginada } from '../../../ver_stock_bodega/models/respuesta-paginada';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista-productos-qf',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lista-productos-qf.component.html',
  styleUrls: ['./lista-productos-qf.component.css'],
})
export class ListaProductosQfComponent implements OnInit {
  productos: ProductoFiltrado[] = [];
  filteredList: ProductoFiltrado[] = [];

  paginaActual = 0;
  totalPaginas = 0;

  filters = {
    nombreComercial: '',
    nombreGenerico: '',
  };

  constructor(
    private stockService: ProductosStockTotalService,
    private router: Router
  ) {}

  ngOnInit() {
    this.buscarProductos();
  }

  buscarProductos(pagina: number = 0) {
    this.paginaActual = pagina;

    this.stockService
      .buscarStockTotal(
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
        error: (err: any) => {
          console.error(err);
          alert('Error obteniendo productos.');
        },
      });
  }

  limpiarFiltros() {
    this.filters = { nombreComercial: '', nombreGenerico: '' };
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

  onEditar(producto: ProductoFiltrado) {
    this.router.navigate(['/editar-producto', producto.idProducto]);
  }

  // OPCIONES DROPDOWN
  get nombresComerciales(): string[] {
    return [
      ...new Set(this.productos.map((p) => p.nombreComercial).filter((x) => x)),
    ];
  }

  get nombresGenericos(): string[] {
    return [
      ...new Set(
        this.productos
          .filter((p) =>
            this.filters.nombreComercial
              ? p.nombreComercial === this.filters.nombreComercial
              : true
          )
          .map((p) => p.nombreGenerico)
          .filter((x) => x)
      ),
    ];
  }
}
