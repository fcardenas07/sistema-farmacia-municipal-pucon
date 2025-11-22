import { Component } from '@angular/core';
import { HeaderAgregarProductoComponent } from "../../components/header-agregar-producto/header-agregar-producto.component";
import { NavbarQfComponent } from "../../../../core/navbar-qf/navbar-qf.component";
import { FormCreacionProductoComponent } from "../../components/form-creacion-producto/form-creacion-producto.component";

@Component({
  selector: 'app-crear-productos-page',
  imports: [HeaderAgregarProductoComponent, NavbarQfComponent, FormCreacionProductoComponent],
  templateUrl: './crear-productos-page.component.html',
  styleUrl: './crear-productos-page.component.css'
})
export class CrearProductosPageComponent {

}
