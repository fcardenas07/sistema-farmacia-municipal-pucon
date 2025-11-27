import { Component } from '@angular/core';
import {NavbarQfComponent} from '../../../../core/navbar-qf/navbar-qf.component';
import {OpcionesQfHomeComponent} from '../../components/opciones-qf-home/opciones-qf-home.component';

@Component({
  selector: 'app-qf-home',
  imports: [NavbarQfComponent, OpcionesQfHomeComponent],
  templateUrl: './qf-home.component.html',
  standalone: true,
  styleUrl: './qf-home.component.css'
})
export class QfHomeComponent {

}
