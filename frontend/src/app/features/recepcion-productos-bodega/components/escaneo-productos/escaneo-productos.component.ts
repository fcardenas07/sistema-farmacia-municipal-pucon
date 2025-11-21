import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { ProductInfo } from '../../models/product-info';
import { LoteInfo, LoteBackendPayload } from '../../models/lote-info';

@Component({
  selector: 'app-escaneo-productos',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './escaneo-productos.component.html',
  styleUrls: ['./escaneo-productos.component.css']
})
export class EscaneoProductosComponent {

  // Lista de lotes añadidos
  batches: LoteInfo[] = [];

  // Formulario del lote
  loteForm!: FormGroup;

  // Producto seleccionado desde el dropdown
  selectedProduct: ProductInfo | null = null;

  // --- CAMPOS NECESARIOS PARA EL DROPDOWN ---
  productoSearch: string = ''; // texto ingresado
  productosFiltrados: ProductInfo[] = []; // lista filtrada con coincidencias

  // Mock: lo que realmente devuelve el backend
  mockBackendProducts: ProductInfo[] = [
    {
      idProducto: "642eaf0f-74a3-4de3-8fc3-146736a389f0",
      nombreComercial: "Paracetamol 500mg",
      nombreGenerico: "Paracetamol",
      categoria: "Analgésicos y Antiinflamatorios",
      imageUrl: null
    },
    {
      idProducto: "b77fbcfa-2abc-42cd-9182-98fa09171f51",
      nombreComercial: "Amoxicilina 500mg",
      nombreGenerico: "Amoxicilina",
      categoria: "Antibiótico",
      imageUrl: null
    },
    {
      idProducto: "c7cbb2fa-11cc-4444-8bb2-39aaf1f0b221",
      nombreComercial: "Losartán 50mg",
      nombreGenerico: "Losartán",
      categoria: "Cardiovascular",
      imageUrl: null
    }
  ];

  constructor(private fb: FormBuilder) {
    this.loteForm = this.fb.group({
      numeroLote: ['', Validators.required],
      fechaElaboracion: ['', Validators.required],
      fechaVencimiento: ['', Validators.required],
      cantidad: [0, [Validators.required, Validators.min(1)]],
      limiteMerma: [0, [Validators.required, Validators.min(0)]],
      codigoBarra: ['', Validators.required],
      idProducto: [null] // <-- se llena al seleccionar producto
    });
  }

  //------------------------------
  //  BUSCAR PRODUCTO POR NOMBRE
  //------------------------------
  buscarProductoPorNombre() {
    const texto = this.productoSearch.toLowerCase().trim();

    if (texto.length === 0) {
      this.productosFiltrados = [];
      return;
    }

    this.productosFiltrados = this.mockBackendProducts.filter(p =>
      p.nombreComercial.toLowerCase().includes(texto)
    );
  }

  //-----------------------------------
  //  SELECCIONAR PRODUCTO DESDE LISTA
  //-----------------------------------
  seleccionarProducto(p: ProductInfo) {
    this.selectedProduct = p;
    this.productoSearch = p.nombreComercial;
    this.productosFiltrados = [];

    // insertamos idProducto al lote
    this.loteForm.patchValue({
      idProducto: p.idProducto
    });
  }

  //-----------------------------------
  //  AÑADIR LOTE COMPLETO
  //-----------------------------------
  addBatch() {
    if (this.loteForm.invalid) {
      alert("Completa todos los datos del lote.");
      return;
    }

    if (!this.selectedProduct) {
      alert("Debes seleccionar un producto.");
      return;
    }

    const lote: LoteInfo = {
      numeroLote: this.loteForm.value.numeroLote,
      fechaElaboracion: this.loteForm.value.fechaElaboracion,
      fechaVencimiento: this.loteForm.value.fechaVencimiento,
      cantidad: this.loteForm.value.cantidad,
      limiteMerma: this.loteForm.value.limiteMerma,
      codigoBarra: this.loteForm.value.codigoBarra,
      product: this.selectedProduct
    };

    this.batches.push(lote);

    console.log("LOTE AÑADIDO:", lote);

    this.loteForm.reset();
    this.productoSearch = '';
    this.selectedProduct = null;
  }

  //-----------------------------------
  //  GENERAR PAYLOAD EXACTO PARA POST
  //-----------------------------------
  getBackendPayload(): LoteBackendPayload[] {
    return this.batches.map(b => ({
      fechaElaboracion: b.fechaElaboracion,
      fechaVencimiento: b.fechaVencimiento,
      estado: "ACTIVO",
      numeroLote: b.numeroLote,
      cantidad: b.cantidad,
      limiteMerma: b.limiteMerma,
      porcentajeOferta: null,
      precioUnitario: null,
      idGuiaIngreso: null,

      codigo: {
        idProducto: b.product.idProducto ?? null,
        codigoBarra: b.codigoBarra,
        tipoCodigo: "EAN",
        activo: true
      }
    }));
  }

  //-----------------------------------
  //  FINALIZAR SIMULANDO POST
  //-----------------------------------
  finalize() {
    const payload = this.getBackendPayload();
    console.log("PAYLOAD FINAL:", payload);
    alert("Revisa consola.");
  }
}
