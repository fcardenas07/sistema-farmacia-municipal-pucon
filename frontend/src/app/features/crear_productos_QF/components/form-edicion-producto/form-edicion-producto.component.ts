import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductosService } from '../../services/productos.service';
import { ProductoCreacion } from '../../models/producto-creacion';

@Component({
  selector: 'app-form-edicion-producto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './form-edicion-producto.component.html',
  styleUrls: ['./form-edicion-producto.component.css'],
})
export class FormEdicionProductoComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private productosService = inject(ProductosService);

  id!: string;
  cargando = true;

  form: Partial<ProductoCreacion> = {
    nombreComercial: '',
    nombreGenerico: '',
    presentacion: '',
    dosificacion: 0,
    unidadMedida: 'mg',
  };

  errors: any = {};
  isValid = false;

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;

    this.productosService.getProducto(this.id).subscribe({
      next: (p) => {
        this.form = {
          nombreComercial: p.nombreComercial,
          nombreGenerico: p.nombreGenerico,
          presentacion: p.presentacion,
          dosificacion: p.dosificacion,
          unidadMedida: p.unidadMedida,
        };

        this.cargando = false;
        this.validate();
      },
      error: () => {
        alert('Error cargando producto');
        this.router.navigate(['/productos']);
      },
    });
  }

  validate() {
    this.errors = {};
    this.isValid = true;

    if (!this.form.nombreComercial!.trim())
      this.errors.nombreComercial = 'El nombre comercial es obligatorio.';

    if (!this.form.nombreGenerico!.trim())
      this.errors.nombreGenerico = 'El nombre genérico es obligatorio.';

    if (!this.form.dosificacion || this.form.dosificacion <= 0)
      this.errors.dosificacion = 'La dosificación debe ser mayor a 0.';

    if (Object.keys(this.errors).length > 0) this.isValid = false;
  }

  guardar() {
    this.validate();
    if (!this.isValid) return;

    this.productosService.editarProducto(this.id, this.form).subscribe({
      next: () => {
        alert('Producto actualizado correctamente');
        this.router.navigate(['/productos-qf']);
      },
      error: () => alert('Error al actualizar el producto'),
    });
  }
}
