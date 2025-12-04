import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProductosStockCriticoService } from '../../services/stock-critico.service';
import { ProductoFiltradoQF } from '../../models/producto-filtrado-qf';
import { RespuestaPaginadaQf } from '../../models/respuesta-paginada-qf';

/* ============================================================
   🎯 MODELO UI
   ============================================================ */
export interface StockProductQF {
  id: string;
  name: string;
  generic: string;
  details: string;
  category: string;
  status: string;
  units: number;
  manufacturer: string;
  dosage: string;
  imageUrl: string;
}

@Component({
  selector: 'app-stock-critico-qf',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './stock-critico-qf.component.html',
  styleUrls: ['./stock-critico-qf.component.css']
})
export class StockCriticoQFComponent implements OnInit {

  /* --------------------- FILTROS --------------------- */
  filters = {
    name: '',
    generic: ''
  };

  /* --------------------- PAGINACIÓN --------------------- */
  currentPage = 0;
  totalPages = 0;

  /* --------------------- LISTA MOSTRADA --------------------- */
  filteredList: StockProductQF[] = [];

  constructor(private stockService: ProductosStockCriticoService) {}

  ngOnInit(): void {
    this.buscarProductos();
  }

  /* ============================================================
     🔍 LLAMADA AL BACKEND PARA QF
     ============================================================ */
  buscarProductos() {
    this.stockService.buscarStockCritico(
      this.currentPage,
      this.filters.name,
      this.filters.generic
    )
    .subscribe({
      next: (resp: RespuestaPaginadaQf<ProductoFiltradoQF>) => {
        this.totalPages = resp.totalPages;
        this.filteredList = resp.content.map(p => this.mapToUI(p));
      },
      error: (err) => console.error("Error al buscar productos QF", err)
    });
  }

  /* ============================================================
     🔄 MAPEO BACKEND → UI
     ============================================================ */
  private mapToUI(p: ProductoFiltradoQF): StockProductQF {
    return {
      id: p.idProducto,
      name: p.nombreComercial,
      generic: p.nombreGenerico,
      details: "",
      category: "",
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
    this.filters = { name: '', generic: '' };
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

  /* ============================================================
     📌 LISTAS DINÁMICAS PARA DROPDOWNS
     ============================================================ */
  get names(): string[] {
    return [...new Set(this.filteredList.map(p => p.name))];
  }

  get generics(): string[] {
    return [...new Set(this.filteredList.map(p => p.generic))];
  }

}
