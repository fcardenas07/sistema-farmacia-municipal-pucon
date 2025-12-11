import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-stockcritico-qf',
  imports: [],
  templateUrl: './header-stockcritico-qf.component.html',
  styleUrl: './header-stockcritico-qf.component.css'
})
export class HeaderStockcriticoQfComponent {
// 2. Inyectamos el router
  constructor(private router: Router) {}

  // 3. Creamos la función de navegación
  irAlStock() {
    this.router.navigate(['/stock-normal-qf']);
  }
}
