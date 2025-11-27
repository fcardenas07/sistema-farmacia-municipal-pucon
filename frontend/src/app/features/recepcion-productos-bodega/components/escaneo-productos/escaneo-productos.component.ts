import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { ProductoBackend } from '../../models/producto-backend';
import { LoteInfo } from '../../models/lote-info';
import { ProductosBodegaService } from '../../services/productos-bodega.service';
import { LotesService } from '../../services/lotes.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-escaneo-productos',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './escaneo-productos.component.html',
  styleUrls: ['./escaneo-productos.component.css']
})
export class EscaneoProductosComponent {

  loteForm!: FormGroup;

  productoSearch: string = '';
  productosFiltrados: ProductoBackend[] = [];
  selectedProduct: ProductoBackend | null = null;

  batches: LoteInfo[] = [];

  // Manejo de errores visuales
  productoError = false;

  constructor(
    private fb: FormBuilder,
    private productosService: ProductosBodegaService,
    private lotesService: LotesService,
    private router: Router
  ) {
    this.loteForm = this.fb.group({
      // 1. DATOS DEL LOTE
      numeroLote: ['', Validators.required],
      fechaElaboracion: ['', Validators.required],
      fechaVencimiento: ['', Validators.required],

      // 3. CONTENIDO DEL LOTE
      cantidad: [null, [Validators.required, Validators.min(1)]],
      limiteMerma: [null, [Validators.required, Validators.min(1)]],
      precioUnitario: [null, [Validators.required, Validators.min(1)]],
      codigoBarra: ['', Validators.required],

      // ID PRODUCTO (LO LLENAMOS AUTOMÁTICAMENTE)
      idProducto: [null, Validators.required]
    });

    this.batches = this.lotesService.getLotes();
  }

  // ===============================
  // BUSCAR PRODUCTOS
  // ===============================
  buscarProductosBackend() {
    const texto = this.productoSearch.trim();
    if (!texto) return;

    this.productosService.buscarProductos(texto).subscribe({
      next: (resp) => {
        this.productosFiltrados = resp;
      },
      error: (err) => console.error("❌ Error al buscar productos", err)
    });
  }

  // ===============================
  // SELECCIONAR PRODUCTO
  // ===============================
  seleccionarProducto(p: ProductoBackend) {
    this.selectedProduct = p;
    this.productoSearch = p.nombreComercial;
    this.productosFiltrados = [];
    this.loteForm.patchValue({ idProducto: p.idProducto });
    this.productoError = false; // quita mensaje de error si ya se seleccionó
  }

  // ===============================
  // AÑADIR LOTE
  // ===============================
  addBatch() {
    this.productoError = false;

    // Validación 1: PRODUCTO DEBE ESTAR SELECCIONADO
    if (!this.selectedProduct) {
      this.productoError = true;
      return;
    }

    // Validación 2: FORMULARIO COMPLETO
    if (this.loteForm.invalid) {
      this.loteForm.markAllAsTouched();
      return;
    }

    const lote: LoteInfo = {
      numeroLote: this.loteForm.value.numeroLote,
      fechaElaboracion: this.loteForm.value.fechaElaboracion,
      fechaVencimiento: this.loteForm.value.fechaVencimiento,
      cantidad: this.loteForm.value.cantidad,
      limiteMerma: this.loteForm.value.limiteMerma,
      precioUnitario: this.loteForm.value.precioUnitario,
      codigoBarra: this.loteForm.value.codigoBarra,
      product: this.selectedProduct
    };

    this.batches.push(lote);
    this.lotesService.setLotes(this.batches);

    // RESET
    this.loteForm.reset();
    this.productoSearch = '';
    this.selectedProduct = null;
  }

  // ===============================
  // ELIMINAR LOTE
  // ===============================
  eliminarLote(index: number) {
    this.batches.splice(index, 1);
    this.lotesService.setLotes(this.batches);
  }

  // ===============================
  // FINALIZAR
  // ===============================
  finalize() {
    this.lotesService.setLotes(this.batches);
    this.router.navigate(['/resumen-pedido']);
  }

}
