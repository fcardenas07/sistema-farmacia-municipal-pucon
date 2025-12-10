import { Component } from '@angular/core';
import { BodegaHomeComponent } from "../../../home/pages/bodega-home/bodega-home.component";
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderStockTotalComponent } from "../../components/header-stock-total/header-stock-total.component";
import { TotalProductosStockComponent } from "../../components/total-productos-stock/total-productos-stock.component";

@Component({
  selector: 'app-stock-total-page',
  imports: [NavbarBodegaComponent, HeaderStockTotalComponent, TotalProductosStockComponent],
  templateUrl: './stock-total-page.component.html',
  styleUrl: './stock-total-page.component.css'
})
export class StockTotalPageComponent {

}
