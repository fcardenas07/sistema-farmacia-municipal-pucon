import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

export interface MermaInventario {
  codigo: string;
  producto: string;
  lote: string;
  cantidadMerma: number;
  tipoMerma: string;
  fecha: string;
  responsable: string;
  descripcion: string;
}

@Component({
  selector: 'app-inventario-mermas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventario-mermas.component.html',
  styleUrl: './inventario-mermas.component.css'
})
export class InventarioMermasComponent {

  mermas: MermaInventario[] = [
    {
      codigo: 'PR-001',
      producto: 'Paracetamol 500mg',
      lote: 'L-9123',
      cantidadMerma: 15,
      tipoMerma: 'Producto dañado',
      fecha: '20/11/2024',
      responsable: 'Sebastián Aliante',
      descripcion: 'Caja dañada durante el transporte. 15 unidades inutilizables.'
    },
    {
      codigo: 'PR-002',
      producto: 'Ibuprofeno 400mg',
      lote: 'L-5521',
      cantidadMerma: 8,
      tipoMerma: 'Error de inventario',
      fecha: '03/01/2025',
      responsable: 'María Torres',
      descripcion: 'Diferencia detectada durante auditoría interna.'
    },
    {
      codigo: 'PR-003',
      producto: 'Omeprazol 20mg',
      lote: 'L-7781',
      cantidadMerma: 20,
      tipoMerma: 'Recal del laboratorio',
      fecha: '15/12/2024',
      responsable: 'Diego Ramos',
      descripcion: 'El lote completo fue retirado por indicación del laboratorio fabricante.'
    }
  ];

  verDetalles(merma: MermaInventario) {
    console.log('Detalles de la merma:', merma);
  }

}
