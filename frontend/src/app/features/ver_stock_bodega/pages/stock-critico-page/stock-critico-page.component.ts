import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderStockComponent } from "../../components/header-stock/header-stock.component";
import { ProductosStockCriticoComponent } from "../../components/productos-stock-critico/productos-stock-critico.component";

@Component({
  selector: 'app-stock-critico-page',
  imports: [NavbarBodegaComponent, HeaderStockComponent, ProductosStockCriticoComponent],
  templateUrl: './stock-critico-page.component.html',
  styleUrl: './stock-critico-page.component.css'
})
export class StockCriticoPageComponent {

}
