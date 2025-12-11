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
  productoError = false;

  constructor(
    private fb: FormBuilder,
    private productosService: ProductosBodegaService,
    private lotesService: LotesService,
    private router: Router
  ) {
    this.loteForm = this.fb.group({
      numeroLote: ['', Validators.required],
      fechaElaboracion: ['', Validators.required],
      fechaVencimiento: ['', Validators.required],
      cantidad: [null, [Validators.required, Validators.min(1)]],
      limiteMerma: [null, [Validators.required, Validators.min(1)]],
      precioUnitario: [null, [Validators.required, Validators.min(1)]],
      codigoBarra: ['', Validators.required],
      idProducto: [null, Validators.required]
    });

    this.batches = this.lotesService.getLotes();
  }

  buscarProductosBackend() {
    const texto = this.productoSearch.trim();
    if (!texto) return;

    this.productosService.buscarProductos(texto).subscribe({
      next: (resp) => {
        this.productosFiltrados = resp;
      },
      error: (err) => console.error(err)
    });
  }

  seleccionarProducto(p: ProductoBackend) {
    this.selectedProduct = p;
    this.productoSearch = p.nombreComercial;
    this.productosFiltrados = [];
    this.loteForm.patchValue({ idProducto: p.idProducto });
    this.productoError = false;
  }

  addBatch() {
    this.productoError = false;

    if (!this.selectedProduct) {
      this.productoError = true;
      return;
    }

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
      product: {
        idProducto: this.selectedProduct.idProducto,
        nombreComercial: this.selectedProduct.nombreComercial,
        nombreFabricante: this.selectedProduct.nombreFabricante,
        urlFoto: this.selectedProduct.urlFoto
      }
    };

    this.batches.push(lote);
    this.lotesService.setLotes(this.batches);

    this.loteForm.reset();
    this.productoSearch = '';
    this.selectedProduct = null;
  }

  eliminarLote(index: number) {
    this.batches.splice(index, 1);
    this.lotesService.setLotes(this.batches);
  }

  finalize() {

    // 🔎 VER QUÉ LOTES QUIERES ENVIAR
    console.log("🟦 Lotes preparados para envío:", this.batches);

    this.lotesService.setLotes(this.batches);
    this.router.navigate(['/resumen-pedido']);
  }
}
