import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderMermaInventarioComponent } from "../../components/header-merma-inventario/header-merma-inventario.component";
import { InventarioMermasComponent } from "../../components/inventario-mermas/inventario-mermas.component";

@Component({
  selector: 'app-ver-invnetario-mermas-page',
  imports: [NavbarBodegaComponent, HeaderMermaInventarioComponent, InventarioMermasComponent],
  templateUrl: './ver-invnetario-mermas-page.component.html',
  styleUrl: './ver-invnetario-mermas-page.component.css'
})
export class VerInvnetarioMermasPageComponent {

}
