import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface StockProduct {
  id: number;
  name: string;
  generic: string;
  details: string;
  category: string;
  status: 'Stock Medio' | 'Muy Bajo';
  units: number;
  imageUrl: string;
  manufacturer: string;
  dosage: string;
}

@Component({
  selector: 'app-productos-stock-critico',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos-stock-critico.component.html',
  styleUrl: './productos-stock-critico.component.css'
})
export class ProductosStockCriticoComponent {

  /* ---------------------- DATOS ---------------------- */

  filters = {
    name: '',
    generic: '',
    details: '',
    category: '',
    dosage: '',
    manufacturer: ''
  };

  products: StockProduct[] = [
    {
      id: 1,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Respiratorio',
      status: 'Stock Medio',
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
      status: 'Muy Bajo',
      units: 5,
      dosage: '50mg',
      manufacturer: 'Saval',
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 3,
      name: 'Amoxicilina 500mg',
      generic: 'Amoxicilina',
      details: '30 Cápsulas',
      category: 'Antibiótico',
      status: 'Stock Medio',
      units: 18,
      dosage: '500mg',
      manufacturer: 'Bayer',
      imageUrl: 'https://via.placeholder.com/100'
    }
  ];

  /* Esta lista es la que realmente se muestra */
  filteredList: StockProduct[] = [...this.products];

  /* ---------------------- OPCIONES DEPENDIENTES ---------------------- */

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

  /* ---------------------- BUSQUEDA REAL ---------------------- */

  buscarProductos() {
    console.log("Enviando filtros al backend:", this.filters);

    // FILTRAMOS LOCALMENTE PERO SOLO AL PULSAR "BUSCAR"
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

  /* ---------------------- ESTILOS ---------------------- */

  getStatusClass(status: string): string {
    return status === 'Muy Bajo' ? 'tag-low' : 'tag-medium';
  }
}
