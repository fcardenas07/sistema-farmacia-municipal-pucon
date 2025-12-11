import {Component} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {LogoutButtonComponent} from "../../shared/components/logout-button/logout-button.component";

@Component({
  selector: 'app-navbar-qf',
  imports: [RouterLink, RouterLinkActive, LogoutButtonComponent],
  templateUrl: './navbar-qf.component.html',
  standalone: true,
  styleUrl: './navbar-qf.component.css'
})
export class NavbarQfComponent {

}
