import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderIngresoCodigoPedidoComponent } from "../../components/header-ingreso-codigo-pedido/header-ingreso-codigo-pedido.component";
import { PasosIngresoCodigoPedidoComponent } from "../../components/pasos-ingreso-codigo-pedido/pasos-ingreso-codigo-pedido.component";
import { FormCodigoPedidoComponent } from "../../components/form-codigo-pedido/form-codigo-pedido.component";

@Component({
  selector: 'app-ingreso-codigo-pedido-page',
  imports: [NavbarBodegaComponent, HeaderIngresoCodigoPedidoComponent, PasosIngresoCodigoPedidoComponent, FormCodigoPedidoComponent],
  templateUrl: './ingreso-codigo-pedido-page.component.html',
  styleUrl: './ingreso-codigo-pedido-page.component.css'
})
export class IngresoCodigoPedidoPageComponent {

}
