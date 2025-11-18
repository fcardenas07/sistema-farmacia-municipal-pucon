import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { PasosResumenPedidoComponent } from "../../components/pasos-resumen-pedido/pasos-resumen-pedido.component";
import { HeaderBodegaHomeComponent } from "../../../home/components/header-bodega-home/header-bodega-home.component";
import { HeaderIngresoCodigoPedidoComponent } from "../../components/header-ingreso-codigo-pedido/header-ingreso-codigo-pedido.component";
import { ResumenPedidoComponent } from "../../components/resumen-pedido/resumen-pedido.component";

@Component({
  selector: 'app-resumen-pedido-page',
  imports: [NavbarBodegaComponent, PasosResumenPedidoComponent, HeaderIngresoCodigoPedidoComponent, ResumenPedidoComponent],
  templateUrl: './resumen-pedido-page.component.html',
  styleUrl: './resumen-pedido-page.component.css'
})
export class ResumenPedidoPageComponent {

}
