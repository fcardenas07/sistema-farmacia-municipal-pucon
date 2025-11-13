import { Component } from '@angular/core';
import { ProductosTablaComponent } from '../../components/productos-tabla-vendedor/productos-tabla-vendedor.component';
import { NavbarVendedorComponent } from "../../../../core/navbar-vendedor/navbar-vendedor.component";

@Component({
  selector: 'app-productos-vendedor',
  standalone: true,
  imports: [ProductosTablaComponent, NavbarVendedorComponent],
  templateUrl: './productos-vendedor.component.html',
  styleUrls: ['./productos-vendedor.component.css']
})
export class ProductosVendedorPageComponent {
}
