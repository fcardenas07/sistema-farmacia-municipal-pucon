import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderMermaInventarioComponent } from "../../components/header-merma-inventario/header-merma-inventario.component";
import { IngresoMermaFormComponent } from "../../components/ingreso-merma-form/ingreso-merma-form.component";

@Component({
  selector: 'app-formulario-merma-page',
  imports: [NavbarBodegaComponent, HeaderMermaInventarioComponent, IngresoMermaFormComponent],
  templateUrl: './formulario-merma-page.component.html',
  styleUrl: './formulario-merma-page.component.css'
})
export class FormularioMermaPageComponent {

}
