import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProductoFormService } from '../../services/producto-form.service';
import { ProductosService } from '../../services/productos.service';
import { ProductoCreacion } from '../../models/producto-creacion';

@Component({
  selector: 'app-producto-resumen',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './producto-resumen.component.html',
  styleUrl: './producto-resumen.component.css'
})
export class ProductoResumenComponent {

  private router = inject(Router);
  private productoFormService = inject(ProductoFormService);
  private productosService = inject(ProductosService);

  producto: ProductoCreacion | null = null;

  constructor() {
    // Cargar el borrador guardado
    this.producto = this.productoFormService.getDraft();
  }

  editar() {
    // Volver al formulario para editar
    this.router.navigate(['/crear-productos']);
  }

  finalizar() {
    if (!this.producto) {
      alert("No hay datos para enviar.");
      return;
    }

    this.productosService.crearProducto(this.producto).subscribe({
      next: () => {
        alert("Producto creado correctamente.");

        // Limpiar borrador
        this.productoFormService.clearDraft();

        // Redirigir a formulario limpio
        this.router.navigate(['/crear-productos']);
      },
      error: (err) => {
        console.error("❌ Error al crear producto:", err);
        alert("Ocurrió un error al crear el producto.");
      }
    });
  }
}
