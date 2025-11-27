import { Component, Input } from '@angular/core';
import { ProductoVentas } from '../../models/producto-ventas';

@Component({
  selector: 'app-producto-card',
  standalone: true,
  imports: [],
  templateUrl: './producto-card.component.html',
  styleUrls: ['./producto-card.component.css']
})
export class ProductoCardComponent {
  @Input() producto!: ProductoVentas;
}
