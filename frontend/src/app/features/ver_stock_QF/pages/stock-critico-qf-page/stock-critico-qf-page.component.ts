import { Component } from '@angular/core';
import { NavbarQfComponent } from "../../../../core/navbar-qf/navbar-qf.component";
import { HeaderStockComponent } from "../../../ver_stock_bodega/components/header-stock/header-stock.component";
import { StockCriticoPageComponent } from "../../../ver_stock_bodega/pages/stock-critico-page/stock-critico-page.component";
import { StockCriticoQFComponent } from "../../components/stock-critico-qf/stock-critico-qf.component";
import { HeaderStockcriticoQfComponent } from "../../components/header-stockcritico-qf/header-stockcritico-qf.component";

@Component({
  selector: 'app-stock-critico-qf-page',
  imports: [NavbarQfComponent,StockCriticoQFComponent, HeaderStockcriticoQfComponent],
  templateUrl: './stock-critico-qf-page.component.html',
  styleUrl: './stock-critico-qf-page.component.css'
})
export class StockCriticoQfPageComponent {

}
