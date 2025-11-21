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

  // Producto escaneado actual
  scannedProduct: ProductInfo | null = null;

  // Formulario del lote actual
  loteForm!: FormGroup;

  // Mock de productos simulando backend
  mockProducts: Record<string, ProductInfo> = {
    "123456789": {
      idProducto: null,
      name: "Lipitor 20mg",
      genericName: "Atorvastatina",
      details: "30 Comprimidos",
      imageUrl: "https://via.placeholder.com/150",
    },
    "555888999": {
      idProducto: null,
      name: "Paracetamol 500mg",
      genericName: "Acetaminofén",
      details: "100 Comprimidos",
      imageUrl: "https://via.placeholder.com/150"
    },
    "987654321": {
      idProducto: null,
      name: "Losartán 50mg",
      genericName: "Losartán",
      details: "30 Comprimidos",
      imageUrl: "https://via.placeholder.com/150"
    }
  };

  constructor(private fb: FormBuilder) {
    this.loteForm = this.fb.group({
      numeroLote: ['', Validators.required],
      fechaElaboracion: ['', Validators.required],
      fechaVencimiento: ['', Validators.required],
      cantidad: [0, [Validators.required, Validators.min(1)]],
      limiteMerma: [0, [Validators.required, Validators.min(0)]],
      codigoBarra: ['', Validators.required]
    });
  }

  // Buscar producto por código
  buscarProducto() {
    const code = this.loteForm.value.codigoBarra;

    if (this.mockProducts[code]) {
      this.scannedProduct = this.mockProducts[code];
    } else {
      this.scannedProduct = null;
      alert("Producto no encontrado");
    }
  }

  // Añadir lote
  addBatch() {
    if (this.loteForm.invalid) {
      alert("Completa todos los datos del lote.");
      return;
    }

    if (!this.scannedProduct) {
      alert("Debes buscar un producto primero.");
      return;
    }

    const lote: LoteInfo = {
      numeroLote: this.loteForm.value.numeroLote,
      fechaElaboracion: this.loteForm.value.fechaElaboracion,
      fechaVencimiento: this.loteForm.value.fechaVencimiento,
      cantidad: this.loteForm.value.cantidad,
      limiteMerma: this.loteForm.value.limiteMerma,
      codigoBarra: this.loteForm.value.codigoBarra,
      product: this.scannedProduct
    };

    this.batches.push(lote);

    console.log("Lotes actualmente:", this.batches);

    // Reset para ingresar otro lote
    this.loteForm.reset();
    this.scannedProduct = null;
  }

  // * GENERAR PAYLOAD EXACTO PARA EL BACKEND
  getBackendPayload(): LoteBackendPayload[] {
    return this.batches.map(b => ({
      fechaElaboracion: b.fechaElaboracion,
      fechaVencimiento: b.fechaVencimiento,
      estado: null,
      numeroLote: b.numeroLote,
      cantidad: b.cantidad,
      limiteMerma: b.limiteMerma,
      porcentajeOferta: null,
      precioUnitario: null,
      idGuiaIngreso: null,

      codigo: {
        idProducto: null,
        codigoBarra: b.codigoBarra,
        tipoCodigo: null,
        activo: null
      }
    }));
  }

  finalize() {
    const payload = this.getBackendPayload();
    console.log("Payload final listo para backend (POST):", payload);
    alert("Revisar consola para ver el payload final.");
  }
}
