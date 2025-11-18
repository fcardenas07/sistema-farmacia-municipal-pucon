import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-opciones-bodega-home',
  standalone: true,
  imports: [],
  templateUrl: './opciones-bodega-home.component.html',
  styleUrls: ['./opciones-bodega-home.component.css']
})
export class OpcionesBodegaHomeComponent {

  constructor(private router: Router) {}

  irARecepcion() {
    this.router.navigate(['/ingreso-codigo-pedido']);
  }

  irAStock() {
    this.router.navigate(['/stock-critico']);
  }
}
