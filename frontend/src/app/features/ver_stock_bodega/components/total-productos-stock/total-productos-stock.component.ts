import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface StockProduct {
  id: number;
  name: string;
  generic: string;
  details: string;
  category: string;
  status: 'Stock normal' | 'Stock Bajo';
  units: number;
  imageUrl: string;
  manufacturer: string;
  dosage: string;
}

@Component({
  selector: 'app-total-productos-stock',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './total-productos-stock.component.html',
  styleUrls: ['./total-productos-stock.component.css']
})
export class TotalProductosStockComponent {

  /* ---------------------- DATOS ---------------------- */

  // Filtros
  filters = {
    name: '',
    generic: '',
    details: '',
    category: '',
    dosage: '',
    manufacturer: ''
  };

  // Lista de productos
  products: StockProduct[] = [
    {
      id: 1,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Respiratorio',
      status: 'Stock normal',
      units: 20,
      dosage: '20mg',
      manufacturer: 'Pfizer',
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 2,
      name: 'Losartan 50mg',
      generic: 'Losartan',
      details: '20 Comprimidos',
      category: 'Cardiovascular',
      status: 'Stock normal',
      units: 10,
      dosage: '50mg',
      manufacturer: 'Saval',
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 3,
      name: 'Paracetamol 5ml',
      generic: 'Acetaminofén',
      details: 'Frasco 120ml',
      category: 'Analgésico',
      status: 'Stock normal',
      units: 12,
      dosage: '5ml',
      manufacturer: 'Medipharm',
      imageUrl: 'https://via.placeholder.com/100'
    }
  ];

  // Esta es la lista mostrada realmente
  filteredList: StockProduct[] = [...this.products];

  /* ---------------------- OPCIONES DINÁMICAS ---------------------- */

  get names(): string[] {
    return [...new Set(this.products.map(p => p.name))];
  }

  get generics(): string[] {
    return [...new Set(
      this.products
        .filter(p => this.filters.name ? p.name === this.filters.name : true)
        .map(p => p.generic)
    )];
  }

  get details(): string[] {
    return [...new Set(
      this.products
        .filter(p => this.filters.generic ? p.generic === this.filters.generic : true)
        .map(p => p.details)
    )];
  }

  get categories(): string[] {
    return [...new Set(
      this.products
        .filter(p => this.filters.details ? p.details === this.filters.details : true)
        .map(p => p.category)
    )];
  }

  get dosages(): string[] {
    return [...new Set(
      this.products
        .filter(p => this.filters.category ? p.category === this.filters.category : true)
        .map(p => p.dosage)
    )];
  }

  get manufacturers(): string[] {
    return [...new Set(
      this.products
        .filter(p => this.filters.dosage ? p.dosage === this.filters.dosage : true)
        .map(p => p.manufacturer)
    )];
  }

  /* ---------------------- BUSCAR ---------------------- */

  buscarProductos() {
    console.log("Enviando filtros al backend:", this.filters);

    this.filteredList = this.products.filter(p =>
      (!this.filters.name || p.name === this.filters.name) &&
      (!this.filters.generic || p.generic === this.filters.generic) &&
      (!this.filters.details || p.details === this.filters.details) &&
      (!this.filters.category || p.category === this.filters.category) &&
      (!this.filters.dosage || p.dosage === this.filters.dosage) &&
      (!this.filters.manufacturer || p.manufacturer === this.filters.manufacturer)
    );
  }

  /* ---------------------- LIMPIAR ---------------------- */
  limpiarFiltros() {
    this.filters = {
      name: '',
      generic: '',
      details: '',
      category: '',
      dosage: '',
      manufacturer: ''
    };

    this.filteredList = [...this.products];
  }

  /* ---------------------- CSS DINÁMICO ---------------------- */
  getStatusClass(status: string): string {
    return status === 'Stock normal' ? 'tag-normal' : 'tag-low';
  }
}
