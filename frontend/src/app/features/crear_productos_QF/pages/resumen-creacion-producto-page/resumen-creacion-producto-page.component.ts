import { Component } from '@angular/core';
import { NavbarQfComponent } from "../../../../core/navbar-qf/navbar-qf.component";
import { HeaderResumenCreacionProductoComponent } from "../../components/header-resumen-creacion-producto/header-resumen-creacion-producto.component";
import { ProductoResumenComponent } from "../../components/producto-resumen/producto-resumen.component";

@Component({
  selector: 'app-resumen-creacion-producto-page',
  imports: [NavbarQfComponent, HeaderResumenCreacionProductoComponent, ProductoResumenComponent],
  templateUrl: './resumen-creacion-producto-page.component.html',
  styleUrl: './resumen-creacion-producto-page.component.css'
})
export class ResumenCreacionProductoPageComponent {

}
