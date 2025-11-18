import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-header-stock',
  imports: [],
  templateUrl: './header-stock.component.html',
  styleUrl: './header-stock.component.css'
})
export class HeaderStockComponent {
// 2. Inyectamos el router
  constructor(private router: Router) {}

  // 3. Creamos la función de navegación
  irAlStock() {
    this.router.navigate(['/stock']);
  }
}
