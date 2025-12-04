import { Component } from '@angular/core';
import { NavbarQfComponent } from "../../../../core/navbar-qf/navbar-qf.component";
import { StockNormalQfComponent } from "../../components/stock-normal-qf/stock-normal-qf.component";
import { HeaderStockTotalComponent } from "../../../ver_stock_bodega/components/header-stock-total/header-stock-total.component";
import { HeaderStocknormalQfComponent } from "../../components/header-stocknormal-qf/header-stocknormal-qf.component";

@Component({
  selector: 'app-stock-normal-qf-page',
  imports: [NavbarQfComponent, StockNormalQfComponent,HeaderStocknormalQfComponent],
  templateUrl: './stock-normal-qf-page.component.html',
  styleUrl: './stock-normal-qf-page.component.css'
})
export class StockNormalQfPageComponent {

}
