import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar-admin',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar-admin.component.html',
  styleUrl: './navbar-admin.component.css',
  standalone: true,
})
export class NavbarAdminComponent {}
