import { Component } from '@angular/core';
import { NavbarQfComponent } from '../../../../core/navbar-qf/navbar-qf.component';
import { FormEdicionProductoComponent } from '../../components/form-edicion-producto/form-edicion-producto.component';

@Component({
  selector: 'app-editar-productos-page',
  imports: [NavbarQfComponent, FormEdicionProductoComponent],
  templateUrl: './editar-producto.component.html',
  styleUrls: ['./editar-producto.component.css'],
})
export class EditarProductosComponent {}
