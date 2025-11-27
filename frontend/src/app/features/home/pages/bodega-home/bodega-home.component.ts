import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderBodegaHomeComponent } from "../../components/header-bodega-home/header-bodega-home.component";
import { OpcionesBodegaHomeComponent } from "../../components/opciones-bodega-home/opciones-bodega-home.component";

@Component({
  selector: 'app-bodega-home',
  imports: [NavbarBodegaComponent, HeaderBodegaHomeComponent, OpcionesBodegaHomeComponent],
  templateUrl: './bodega-home.component.html',
  styleUrl: './bodega-home.component.css'
})
export class BodegaHomeComponent {

}
