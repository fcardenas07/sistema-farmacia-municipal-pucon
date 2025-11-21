import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

interface ProductInfo {
  name: string;
  genericName: string;
  details: string;
  imageUrl: string;
}

interface LoteInfo {
  numeroLote: string;
  fechaElaboracion: string;
  fechaVencimiento: string;
  cantidad: number;
  limiteMerma: number;
  codigoBarra: string;
  product?: ProductInfo; 
}

@Component({
  selector: 'app-escaneo-productos',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './escaneo-productos.component.html',
  styleUrls: ['./escaneo-productos.component.css']
})
export class EscaneoProductosComponent {

  // Array de lotes
  batches: LoteInfo[] = [];

  // Formulario principal de un lote
  loteForm!: FormGroup;

  // Producto escaneado
  scannedProduct: ProductInfo | null = null;

  // Medicamentos de prueba simulando un backend
  mockProducts: Record<string, ProductInfo> = {
    "123456789": {
      name: "Lipitor 20mg",
      genericName: "Atorvastatina",
      details: "30 Comprimidos",
      imageUrl: "https://via.placeholder.com/150"
    },
    "555888999": {
      name: "Paracetamol 500mg",
      genericName: "Acetaminofén",
      details: "100 Comprimidos",
      imageUrl: "https://via.placeholder.com/150"
    },
    "987654321": {
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

  buscarProducto() {
    const code = this.loteForm.value.codigoBarra;

    if (this.mockProducts[code]) {
      this.scannedProduct = this.mockProducts[code];
    } else {
      this.scannedProduct = null;
      alert("Producto no encontrado");
    }
  }

  addBatch() {
    if (this.loteForm.invalid) {
      alert("Completa todos los datos del lote.");
      return;
    }

    const lote: LoteInfo = {
      ...this.loteForm.value,
      product: this.scannedProduct!
    };

    this.batches.push(lote);

    console.log("Lotes actuales:", this.batches);

    // Reset para añadir otro lote
    this.loteForm.reset();
    this.scannedProduct = null;
  }

  finalize() {
    console.log("Lotes enviados al backend:", this.batches);
    alert("Proceso finalizado. Revisar consola.");
  }
}
