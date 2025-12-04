import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderInventarioLoteComponent } from "../../components/header-inventario-lote/header-inventario-lote.component";
import { InventarioLotesComponent } from "../../components/inventario-lotes/inventario-lotes.component";

@Component({
  selector: 'app-ver-inventario-lotes-page',
  imports: [NavbarBodegaComponent, HeaderInventarioLoteComponent, InventarioLotesComponent],
  templateUrl: './ver-inventario-lotes-page.component.html',
  styleUrl: './ver-inventario-lotes-page.component.css'
})
export class VerInventarioLotesPageComponent {

}
