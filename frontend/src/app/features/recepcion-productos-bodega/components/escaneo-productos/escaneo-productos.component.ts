import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-escaneo-productos',
  imports: [FormsModule,CommonModule,ReactiveFormsModule],
  templateUrl: './escaneo-productos.component.html',
  styleUrl: './escaneo-productos.component.css'
})
export class EscaneoProductosComponent {
  scanForm: FormGroup;
  
  // Simulamos un producto encontrado para que se vea la tarjeta de la derecha
  scannedProduct = {
    name: 'Lipitor 20mg',
    genericName: 'Atorvastatina',
    details: '30 Comprimidos',
    imageUrl: 'https://via.placeholder.com/150' // Placeholder para la imagen
  };

  constructor(private fb: FormBuilder) {
    this.scanForm = this.fb.group({
      code: ['', Validators.required],
      quantity: [30, [Validators.required, Validators.min(1)]]
    });
  }

  addBatch() {
    console.log('Añadir otro lote', this.scanForm.value);
    // Aquí iría la lógica para limpiar y escanear de nuevo
  }

  finalize() {
    console.log('Finalizar proceso');
  }

}
