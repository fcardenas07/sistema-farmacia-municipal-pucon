import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";

interface PedidoItem {
  id: string;
  productName: string;
  genericName: string;
  details: string;
  imageUrl: string;
  batch: string;
  stock: number;
}

@Component({
  selector: 'app-resumen-pedido',
  standalone: true, // Es buena práctica ponerlo explícito
  imports: [CommonModule], // Agregamos los módulos aquí
  templateUrl: './resumen-pedido.component.html',
  styleUrl: './resumen-pedido.component.css'
})
export class ResumenPedidoComponent {

  // Inicializamos la lista con 1 solo elemento de prueba
  pedidoItems: PedidoItem[] = [
    {
      id: '1',
      productName: 'Lipitor 20mg',
      genericName: 'Atorvastatina',
      details: '30 Comprimidos',
      imageUrl: 'https://via.placeholder.com/150',
      batch: 'JH7421',
      stock: 30
    }
  ];

  // Método para eliminar ítems de la lista visualmente
  removeItem(id: string) {
    this.pedidoItems = this.pedidoItems.filter(item => item.id !== id);
  }

  // Acción del botón finalizar
  completeOrder() {
    console.log('Pedido completado', this.pedidoItems);
  }
}