// ingreso-merma-form.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ingreso-merma-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ingreso-merma-form.component.html',
  styleUrls: ['./ingreso-merma-form.component.css']
})
export class IngresoMermaFormComponent {

  form = {
    codigoProducto: '',
    informacionLote: '',
    numeroLote: '',
    cantidadMerma: null,
    tipoMerma: '',
    descripcion: ''
  };

  opcionesMerma = [
    "Producto dañado",
    "Ruptura de stock",
    "Robo / extravío",
    "Error de inventario",
    "Recal del laboratorio"
  ];

  buscarProducto() {
    console.log("Buscar producto:", this.form.codigoProducto);

    // MOCK TEMPORAL
    this.form.informacionLote = "Paracetamol 500mg - 120 unidades";
    this.form.numeroLote = "L-9123";
  }

  finalizar() {
    console.log("Formulario enviado:", this.form);
  }

  limpiar() {
    this.form = {
      codigoProducto: '',
      informacionLote: '',
      numeroLote: '',
      cantidadMerma: null,
      tipoMerma: '',
      descripcion: ''
    };
  }
}
