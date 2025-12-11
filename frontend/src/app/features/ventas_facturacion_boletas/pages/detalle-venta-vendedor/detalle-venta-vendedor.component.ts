import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {DetalleProductosComponent} from '../../components/detalle-productos/detalle-productos.component';
import {DetalleTotalPagoComponent} from '../../components/detalle-total-pago/detalle-total-pago.component';
import {DetalleOpcionesPagoComponent} from '../../components/detalle-opciones-pago/detalle-opciones-pago.component';
import {NavbarVendedorComponent} from '../../../../core/navbar-vendedor/navbar-vendedor.component';

@Component({
  selector: 'app-detalle-venta-vendedor',
  imports: [DetalleProductosComponent, DetalleTotalPagoComponent, DetalleOpcionesPagoComponent, NavbarVendedorComponent],
  templateUrl: './detalle-venta-vendedor.component.html',
  standalone: true,
  styleUrl: './detalle-venta-vendedor.component.css'
})
export class DetalleVentaVendedorComponent {

  productoSeleccionado: any;

  constructor(private router: Router) {
    this.productoSeleccionado = this.router.getCurrentNavigation()?.extras.state?.['producto'];
    console.log("Producto recibido:", this.productoSeleccionado);
  }
}
