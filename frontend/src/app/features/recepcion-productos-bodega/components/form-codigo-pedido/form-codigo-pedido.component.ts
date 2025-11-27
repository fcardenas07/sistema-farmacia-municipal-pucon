import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-codigo-pedido',
  imports: [FormsModule],
  templateUrl: './form-codigo-pedido.component.html',
  styleUrl: './form-codigo-pedido.component.css'
})
export class FormCodigoPedidoComponent {
codigoPedido: string = '';

  continuar() {
    console.log('Código ingresado:', this.codigoPedido);
    // Aquí puedes navegar a la siguiente página si quieres
    // this.router.navigate(['/ruta']);
  }
}
