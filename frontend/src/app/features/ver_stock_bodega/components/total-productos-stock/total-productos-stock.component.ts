import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

interface StockProduct {
  id: number;
  name: string;
  generic: string;
  details: string;
  category: string;
  status: 'Stock normal' | 'Stock Bajo'; // Ajustado a la nueva imagen
  units: number;
  imageUrl: string;
}

@Component({
  selector: 'app-total-productos-stock',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './total-productos-stock.component.html',
  styleUrls: ['./total-productos-stock.component.css']
})
export class TotalProductosStockComponent {

  products: StockProduct[] = [
    {
      id: 1,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Resperatorio',
      status: 'Stock normal',
      units: 20,
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 2,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Resperatorio',
      status: 'Stock normal',
      units: 20,
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 3,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Resperatorio',
      status: 'Stock normal',
      units: 20,
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 4,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Resperatorio',
      status: 'Stock normal',
      units: 20,
      imageUrl: 'https://via.placeholder.com/100'
    }
  ];

  getStatusClass(status: string): string {
    // Retorna la clase CSS para el color verde si es normal
    return status === 'Stock normal' ? 'tag-normal' : 'tag-low';
  }

  updateProduct(product: StockProduct) {
    console.log('Actualizar', product.name);
  }
}