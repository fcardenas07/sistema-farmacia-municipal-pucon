import { Component } from '@angular/core';
import { NavbarQfComponent } from '../../../../core/navbar-qf/navbar-qf.component';
import { ListaProductosQfComponent } from '../../../crear_productos_QF/components/lista-productos-qf/lista-productos-qf.component';

@Component({
  selector: 'app-qf-home',
  imports: [NavbarQfComponent, ListaProductosQfComponent],
  templateUrl: './qf-home.component.html',
  standalone: true,
  styleUrl: './qf-home.component.css',
})
export class QfHomeComponent {}
