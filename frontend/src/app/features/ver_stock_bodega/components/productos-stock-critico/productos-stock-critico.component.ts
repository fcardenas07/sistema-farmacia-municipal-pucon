import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
interface StockProduct {
  id: number;
  name: string;      // Ej: Lipitor 20mg
  generic: string;   // Ej: Atorvastatina
  details: string;   // Ej: 30 Comprimidos
  category: string;  // Ej: Respiratorio
  status: 'Stock Medio' | 'Muy Bajo'; // Estado para el color
  units: number;     // Ej: 20
  imageUrl: string;
}

@Component({
  selector: 'app-productos-stock-critico',
  imports: [CommonModule],
  templateUrl: './productos-stock-critico.component.html',
  styleUrl: './productos-stock-critico.component.css'
})
export class ProductosStockCriticoComponent {
  products: StockProduct[] = [
    {
      id: 1,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Respiratorio',
      status: 'Stock Medio',
      units: 20,
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 2,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Respiratorio',
      status: 'Muy Bajo',
      units: 5,
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 3,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Respiratorio',
      status: 'Stock Medio',
      units: 20,
      imageUrl: 'https://via.placeholder.com/100'
    },
    {
      id: 4,
      name: 'Lipitor 20mg',
      generic: 'Atorvastatina',
      details: '30 Comprimidos',
      category: 'Respiratorio',
      status: 'Muy Bajo',
      units: 20,
      imageUrl: 'https://via.placeholder.com/100'
    }
  ];

  // Función para asignar clase CSS según el estado
  getStatusClass(status: string): string {
    return status === 'Muy Bajo' ? 'tag-low' : 'tag-medium';
  }

  updateProduct(product: StockProduct) {
    console.log('Actualizando producto:', product.name);
  }

}
