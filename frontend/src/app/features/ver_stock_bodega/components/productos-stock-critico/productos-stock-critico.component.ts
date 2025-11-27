import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductosStockCriticoService } from '../../services/productos-stock-critico.service';

import { ProductoFiltrado } from '../../models/producto-filtrado';
import { RespuestaPaginada } from '../../models/respuesta-paginada';

export interface StockProduct {
  id: string;
  name: string;
  generic: string;
  details: string;        // No viene del backend → vacío
  category: string;       // No viene del backend → vacío
  status: string;
  units: number;
  manufacturer: string;
  dosage: string;
  imageUrl: string;
}

@Component({
  selector: 'app-productos-stock-critico',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos-stock-critico.component.html',
  styleUrls: ['./productos-stock-critico.component.css']
})
export class ProductosStockCriticoComponent implements OnInit {

  /* --------------------- FILTROS --------------------- */

  filters = {
    name: '',
    generic: '',
    manufacturer: ''
  };

  /* --------------------- PAGINACIÓN --------------------- */

  currentPage = 0;
  totalPages = 0;

  /* --------------------- LISTA FINAL MOSTRADA --------------------- */

  filteredList: StockProduct[] = [];

  constructor(private stockService: ProductosStockCriticoService) {}

  ngOnInit(): void {
    this.buscarProductos();
  }

  /* ============================================================
     🔍 LLAMADA AL BACKEND PARA BUSCAR STOCK CRÍTICO
     ============================================================ */

  buscarProductos() {
    this.stockService.buscarStockCritico(
      this.currentPage,
      this.filters.name,
      this.filters.generic,
    )
    .subscribe({
      next: (resp: RespuestaPaginada<ProductoFiltrado>) => {
        this.totalPages = resp.totalPages;

        this.filteredList = resp.content.map(p => this.mapToUI(p));
      },
      error: (err) => {
        console.error("Error al buscar productos críticos", err);
      }
    });
  }

  /* ============================================================
     🔄 MAPEO DEL MODELO DEL BACKEND → MODELO UI (tarjetas)
     ============================================================ */

  private mapToUI(p: ProductoFiltrado): StockProduct {
    return {
      id: p.idProducto,
      name: p.nombreComercial,
      generic: p.nombreGenerico,
      details: "",                     // backend no lo entrega
      category: "",                    // backend no lo entrega
      status: p.estadoStockTitulo,
      units: p.stockTotal,
      manufacturer: p.nombreFabricante ?? "Sin fabricante",
      dosage: `${p.dosificacion}${p.unidadMedida}`,
      imageUrl: p.urlFoto ?? "https://placehold.co/100x100"
    };
  }

  /* ============================================================
     🔄 LIMPIAR FILTROS
     ============================================================ */

  limpiarFiltros() {
    this.filters = {
      name: '',
      generic: '',
      manufacturer: ''
    };
    this.currentPage = 0;
    this.buscarProductos();
  }

  /* ============================================================
     ⏪⏩ PAGINACIÓN
     ============================================================ */

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.buscarProductos();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.buscarProductos();
    }
  }

  /* ============================================================
     🎨 ESTILOS
     ============================================================ */

  getStatusClass(status: string): string {
    switch (status) {
      case "Muy Bajo":
        return "tag-low";
      case "Bajo":
        return "tag-low";
      case "Medio":
        return "tag-medium";
      case "Normal":
        return "tag-normal";
      default:
        return "tag-normal";
    }
  }
}
