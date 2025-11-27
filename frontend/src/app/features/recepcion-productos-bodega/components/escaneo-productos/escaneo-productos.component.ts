import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { ProductosBodegaService } from '../../services/productos-bodega.service';
import { ProductoBackend } from '../../models/producto-backend';
import { LoteInfo } from '../../models/lote-info';
import { Router } from '@angular/router';
import { LotesService } from '../../services/lotes.service';

@Component({
  selector: 'app-escaneo-productos',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './escaneo-productos.component.html',
  styleUrls: ['./escaneo-productos.component.css']
})
export class EscaneoProductosComponent {

  loteForm!: FormGroup;

  productoSearch: string = '';
  productosFiltrados: ProductoBackend[] = [];
  selectedProduct: ProductoBackend | null = null;

  batches: LoteInfo[] = [];

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
      cantidad: [0, [Validators.required, Validators.min(1)]],
      limiteMerma: [0, Validators.required],
      precioUnitario: [null, Validators.required],
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
        this.productosFiltrados = resp.content;
      },
      error: (err) => console.error(err)
    });
  }

  seleccionarProducto(p: ProductoBackend) {
    this.selectedProduct = p;
    this.productoSearch = p.nombreComercial;
    this.productosFiltrados = [];
    this.loteForm.patchValue({ idProducto: p.idProducto });
  }

  addBatch() {
    if (this.loteForm.invalid || !this.selectedProduct) {
      alert('Completa todos los campos');
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

    this.loteForm.reset();
    this.productoSearch = '';
    this.selectedProduct = null;
  }

  eliminarLote(index: number) {
    this.batches.splice(index, 1);
    this.lotesService.setLotes(this.batches);
  }

  finalize() {
    this.lotesService.setLotes(this.batches);
    this.router.navigate(['/resumen-pedido']);
  }
}
