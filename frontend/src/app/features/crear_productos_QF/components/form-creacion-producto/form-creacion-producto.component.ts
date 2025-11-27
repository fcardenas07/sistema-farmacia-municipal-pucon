import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductoFormService } from '../../services/producto-form.service';
import { ProductoCreacion } from '../../models/producto-creacion';

@Component({
  selector: 'app-form-creacion-producto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './form-creacion-producto.component.html',
  styleUrls: ['./form-creacion-producto.component.css']
})
export class FormCreacionProductoComponent implements OnInit {

  private router = inject(Router);
  private productoFormService = inject(ProductoFormService);

  form: ProductoCreacion = {
    nombreComercial: '',
    nombreGenerico: '',
    presentacion: '',
    dosificacion: 0,
    unidadMedida: 'mg',
    stockMinimo: 0,
    stockMaximo: 0,
    categoria: 'ANALGESICOS_ANTIINFLAMATORIOS',
    idFabricante: ''
  };

  errors: any = {};
  isValid: boolean = false;

  ngOnInit(): void {
    const draft = this.productoFormService.getDraft();
    if (draft) this.form = { ...draft };
    this.validate();
  }

  // VALIDACIÓN COMPLETA
  validate() {
    this.errors = {};
    this.isValid = true;

    // NOMBRE COMERCIAL
    if (!this.form.nombreComercial || this.form.nombreComercial.trim() === '') {
      this.errors.nombreComercial = 'El nombre comercial es obligatorio.';
    }

    // NOMBRE GENERICO
    if (!this.form.nombreGenerico || this.form.nombreGenerico.trim() === '') {
      this.errors.nombreGenerico = 'El nombre genérico es obligatorio.';
    }

    // DOSIFICACION
    if (!this.form.dosificacion || this.form.dosificacion <= 0) {
      this.errors.dosificacion = 'La dosificación debe ser mayor a 0.';
    }

    // STOCK MÍNIMO
    if (this.form.stockMinimo! < 0) {
      this.errors.stockMinimo = 'El stock mínimo no puede ser negativo.';
    }

    // STOCK MÁXIMO
    if (this.form.stockMaximo! < 0) {
      this.errors.stockMaximo = 'El stock máximo no puede ser negativo.';
    }

    // STOCK MAX ≥ STOCK MIN
    if (
      this.form.stockMinimo != null &&
      this.form.stockMaximo != null &&
      this.form.stockMaximo < this.form.stockMinimo
    ) {
      this.errors.stockMaximo = 'El stock máximo debe ser mayor o igual al stock mínimo.';
    }

    // Si hay errores → formulario inválido
    if (Object.keys(this.errors).length > 0) this.isValid = false;
  }

  continuar() {
    this.validate();
    if (!this.isValid) return;

    this.productoFormService.setDraft(this.form);
    this.router.navigate(['/resumen-creacion-producto']);
  }

  vaciarFormulario() {
    this.form = {
      nombreComercial: '',
      nombreGenerico: '',
      presentacion: '',
      dosificacion: 0,
      unidadMedida: 'mg',
      stockMinimo: 0,
      stockMaximo: 0,
      categoria: 'ANALGESICOS_ANTIINFLAMATORIOS',
      idFabricante: ''
    };

    this.errors = {};
    this.isValid = false;

    this.productoFormService.clearDraft();
  }
}
