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
    categoria: 'ANALGESICOS_ANTIINFLAMATORIOS'
  };

  ngOnInit(): void {
    const draft = this.productoFormService.getDraft();
    if (draft) {
      this.form = { ...draft };
    }
  }

  continuar() {
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
      categoria: 'ANALGESICOS_ANTIINFLAMATORIOS'
    };

    this.productoFormService.clearDraft();
  }
}
