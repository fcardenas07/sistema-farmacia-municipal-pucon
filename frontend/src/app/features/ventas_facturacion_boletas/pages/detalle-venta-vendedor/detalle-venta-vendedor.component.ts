import { Component } from '@angular/core';
import { DetalleProductosComponent } from "../../components/detalle-productos/detalle-productos.component";
import { DetalleTotalPagoComponent } from "../../components/detalle-total-pago/detalle-total-pago.component";
import { DetalleOpcionesPagoComponent } from "../../components/detalle-opciones-pago/detalle-opciones-pago.component";
import { NavbarVendedorComponent } from '../../../../core/navbar-vendedor/navbar-vendedor.component';
@Component({
  selector: 'app-detalle-venta-vendedor',
  imports: [DetalleProductosComponent, DetalleTotalPagoComponent, DetalleOpcionesPagoComponent, NavbarVendedorComponent],
  templateUrl: './detalle-venta-vendedor.component.html',
  styleUrl: './detalle-venta-vendedor.component.css'
})
export class DetalleVentaVendedorComponent {

}
