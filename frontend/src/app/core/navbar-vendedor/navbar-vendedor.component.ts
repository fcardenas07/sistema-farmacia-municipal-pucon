import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar-vendedor',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar-vendedor.component.html',
  styleUrls: ['./navbar-vendedor.component.css']
})
export class NavbarVendedorComponent {

  // Si en el futuro quieres obtener el usuario logueado, puedes hacerlo aquí.
  // Por ahora no es necesario ningún método extra: routerLinkActive hace todo.
  
}
