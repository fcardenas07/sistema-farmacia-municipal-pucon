import { Component } from '@angular/core';
import {NavbarQfComponent} from '../../../../core/navbar-qf/navbar-qf.component';
import { ListaProductosQfComponent } from "../../components/lista-productos-qf/lista-productos-qf.component";

@Component({
  selector: 'app-productos-qf-page',
  imports: [
    NavbarQfComponent,
    ListaProductosQfComponent
],
  templateUrl: './productos-qf-page.component.html',
  standalone: true,
  styleUrl: './productos-qf-page.component.css'
})
export class ProductosQfPageComponent {

}
