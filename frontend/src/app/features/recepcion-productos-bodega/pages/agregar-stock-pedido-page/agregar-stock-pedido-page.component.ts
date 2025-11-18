import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { PasosAgregarStockComponent } from "../../components/pasos-agregar-stock/pasos-agregar-stock.component";
import { EscaneoProductosComponent } from "../../components/escaneo-productos/escaneo-productos.component";
import { HeaderBodegaHomeComponent } from "../../../home/components/header-bodega-home/header-bodega-home.component";

@Component({
  selector: 'app-agregar-stock-pedido-page',
  imports: [NavbarBodegaComponent, PasosAgregarStockComponent, EscaneoProductosComponent, HeaderBodegaHomeComponent],
  templateUrl: './agregar-stock-pedido-page.component.html',
  styleUrl: './agregar-stock-pedido-page.component.css'
})
export class AgregarStockPedidoPageComponent {

}
