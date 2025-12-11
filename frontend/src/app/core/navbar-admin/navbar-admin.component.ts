import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import {LogoutButtonComponent} from '../../shared/components/logout-button/logout-button.component';

@Component({
  selector: 'app-navbar-admin',
  imports: [RouterLink, RouterLinkActive, LogoutButtonComponent],
  templateUrl: './navbar-admin.component.html',
  styleUrl: './navbar-admin.component.css',
  standalone: true,
})
export class NavbarAdminComponent {}
