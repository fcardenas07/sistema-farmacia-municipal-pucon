import {Component, Input, Output, EventEmitter} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {ProductoVentas} from '../../models/producto-ventas';

@Component({
  selector: 'app-producto-card',
  standalone: true,
  imports: [CommonModule, NgOptimizedImage],
  templateUrl: './producto-card.component.html',
  styleUrls: ['./producto-card.component.css']
})
export class ProductoCardComponent {

  @Input() producto!: ProductoVentas;

  @Output() agregar = new EventEmitter<ProductoVentas>();  // 👈 EVENTO

  precioRandom: number = 0;

  constructor() {
    this.precioRandom = this.getRandomPrecio();
  }

  getRandomPrecio(): number {
    const random = Math.floor(Math.random() * (9000 - 1000 + 1)) + 1000;
    return Math.round(random / 100) * 100;
  }

  ngOnChanges() {
    this.precioRandom = this.getRandomPrecio();
  }

  onAgregar() {
    this.agregar.emit(this.producto);
  }
}
