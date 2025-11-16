import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductoCardComponent } from '../producto-card/producto-card.component';
import { ProductoVentas } from '../../models/producto-ventas';

@Component({
  selector: 'app-productos-tabla-vendedor',
  standalone: true,
  imports: [CommonModule, ProductoCardComponent],
  templateUrl: './productos-tabla-vendedor.component.html',
  styleUrls: ['./productos-tabla-vendedor.component.css']
})
export class ProductosTablaComponent {
  productos: ProductoVentas[] = [
    {
      nombre: 'Lipitor 20mg',
      principioActivo: 'Atorvastatina',
      presentacion: '30 Comprimidos',
      categoria: 'Respiratorio',
      disponibilidad: 'En Stock',
      requiereReceta: 'Sin receta',
      unidades: '20 Unidades',
      precio: '5000',
      ubicacion: 'Buscar en otro sucursal'
    },
    {
      nombre: 'Omeprazol 20mg',
      principioActivo: 'Prilosec',
      presentacion: '30 Comprimidos',
      categoria: 'Cardiovascular',
      disponibilidad: 'En Stock',
      requiereReceta: 'Con Receta',
      unidades: '20 Unidades',
      precio: '3000',
      ubicacion: 'Buscar en otro sucursal'
    },
    {
      nombre: 'Zocor 10Mg',
      principioActivo: 'Simvastatina',
      presentacion: '30 Comprimidos',
      categoria: 'Diabetes',
      disponibilidad: 'En Stock',
      requiereReceta: 'Sin receta',
      unidades: '20 Unidades',
      precio: '3590',
      ubicacion: 'Buscar en otro sucursal'
    },
    {
      nombre: 'Norvasc 10Mg',
      principioActivo: 'Amlodipino',
      presentacion: '30 Comprimidos',
      categoria: 'Hipertensión',
      disponibilidad: 'En Stock',
      requiereReceta: 'Con Receta',
      unidades: '20 Unidades',
      precio: '4990',
      ubicacion: 'Buscar en otro sucursal'
    }
  ];
}
