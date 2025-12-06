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

  // Formulario del lote
  loteForm!: FormGroup;

  // Buscador de productos
  productoSearch: string = '';
  productosFiltrados: ProductoBackend[] = [];
  selectedProduct: ProductoBackend | null = null;

  // Lotes añadidos
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

    // Recuperar lotes guardados temporalmente
    this.batches = this.lotesService.getLotes();
  }

  // ===============================
  //     BUSCAR PRODUCTOS BACKEND
  // ===============================
  buscarProductosBackend() {
    const texto = this.productoSearch.trim();
    if (!texto) return;

    this.productosService.buscarProductos(texto).subscribe({
      next: (resp) => {
        // Resp ahora devuelve: idProducto, nombreComercial, nombreFabricante, urlFoto
        this.productosFiltrados = resp;
      },
      error: (err) => console.error(err)
    });
  }

  // ===============================
  //   SELECCIONAR PRODUCTO
  // ===============================
  seleccionarProducto(p: ProductoBackend) {
    this.selectedProduct = p;

    // Mostrar en el input
    this.productoSearch = p.nombreComercial;

    // Ocultar lista
    this.productosFiltrados = [];

    // Relacionar lote con producto
    this.loteForm.patchValue({ idProducto: p.idProducto });
  }

  // ===============================
  //      AGREGAR LOTE
  // ===============================
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
      product: {
        idProducto: this.selectedProduct.idProducto,
        nombreComercial: this.selectedProduct.nombreComercial,
        nombreFabricante: this.selectedProduct.nombreFabricante,
        urlFoto: this.selectedProduct.urlFoto
      }
    };

    this.batches.push(lote);
    this.lotesService.setLotes(this.batches);

    // Reset form y búsqueda
    this.loteForm.reset();
    this.productoSearch = '';
    this.selectedProduct = null;
  }

  // ===============================
  //       ELIMINAR LOTE
  // ===============================
  eliminarLote(index: number) {
    this.batches.splice(index, 1);
    this.lotesService.setLotes(this.batches);
  }

  // ===============================
  //          FINALIZAR
  // ===============================
  finalize() {
    this.lotesService.setLotes(this.batches);
    this.router.navigate(['/resumen-pedido']);
  }
}
