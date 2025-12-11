import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import {LogoutButtonComponent} from '../../shared/components/logout-button/logout-button.component';

@Component({
  selector: 'app-navbar-bodega',
  imports: [RouterLink, RouterLinkActive, LogoutButtonComponent],
  templateUrl: './navbar-bodega.component.html',
  standalone: true,
  styleUrl: './navbar-bodega.component.css'
})
export class NavbarBodegaComponent {

}
