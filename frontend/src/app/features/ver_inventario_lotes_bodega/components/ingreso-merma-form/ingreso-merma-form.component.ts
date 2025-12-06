import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MermasService } from '../../services/mermas.service';
import { MermaRequest } from '../../models/merma-request';

@Component({
  selector: 'app-ingreso-merma-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ingreso-merma-form.component.html',
  styleUrls: ['./ingreso-merma-form.component.css']
})
export class IngresoMermaFormComponent {

  form = {
    idLote: '',
    tipoMerma: '',
    detalle: '',
    cantidad: null as number | null
  };

  opcionesMerma = [
    "PRODUCTO_DANIADO",
    "RUPTURA_STOCK",
    "ROBO_O_EXTRAVIO",
    "ERROR_INVENTARIO",
    "RECALL_LABORATORIO"
  ];

  constructor(private mermasService: MermasService) {}

finalizar() {
  const payload: MermaRequest = {
    idLote: this.form.idLote.trim(),
    tipoMerma: this.form.tipoMerma,
    detalle: this.form.detalle.trim(),
    cantidad: Number(this.form.cantidad),
  };


  this.mermasService.registrarMerma(payload).subscribe({
    next: (resp) => {
      console.log("✅ Merma registrada, ID generado:", resp);

      alert(`✔ Merma registrada correctamente.\nID generado: ${resp}`);

      this.limpiar();
    },
    error: (err) => {
      console.error("❌ Error registrando merma:", err);
      alert("❌ Ocurrió un error al registrar la merma. Intente nuevamente.");
    }
  });
}


  limpiar() {
    this.form = {
      idLote: '',
      tipoMerma: '',
      detalle: '',
      cantidad: null
    };
  }
}
