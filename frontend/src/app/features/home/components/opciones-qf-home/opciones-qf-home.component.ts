import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-opciones-qf-home',
  imports: [],
  templateUrl: './opciones-qf-home.component.html',
  standalone: true,
  styleUrl: './opciones-qf-home.component.css'
})
export class OpcionesQfHomeComponent {
  constructor(private router: Router) {}

  irACrearProducto() {
    this.router.navigate(['/crear-productos']);
  }

  irAModificarProducto() {
    this.router.navigate(['/modificar-productos']);
  }
}
