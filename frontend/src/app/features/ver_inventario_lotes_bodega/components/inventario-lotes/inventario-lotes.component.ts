// inventario-lotes.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface LoteInventario {
  codigo: string;
  producto: string;
  lote: string;
  fElab: string;
  fVen: string;
  stock: number;
  estado: string;
  ultimoMov: string;
  tipoMov: string;
}

@Component({
  selector: 'app-inventario-lotes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventario-lotes.component.html',
  styleUrls: ['./inventario-lotes.component.css']
})
export class InventarioLotesComponent {

  lotes: LoteInventario[] = [
    {
      codigo: 'PR-001',
      producto: 'Paracetamol 500mg',
      lote: 'L-9123',
      fElab: '01-2023',
      fVen: '10-2025',
      stock: 120,
      estado: 'Por vencer',
      ultimoMov: '20/11/2023',
      tipoMov: 'Ingreso'
    },
    {
      codigo: 'PR-002',
      producto: 'Paracetamol 1000mg',
      lote: 'L-10123',
      fElab: '01-2024',
      fVen: '10-2026',
      stock: 130,
      estado: 'Vencido',
      ultimoMov: '20/11/2027',
      tipoMov: 'Merma'
    }
  ];

  verDetalles(lote: LoteInventario) {
    console.log('Detalles del lote:', lote);
  }
}
