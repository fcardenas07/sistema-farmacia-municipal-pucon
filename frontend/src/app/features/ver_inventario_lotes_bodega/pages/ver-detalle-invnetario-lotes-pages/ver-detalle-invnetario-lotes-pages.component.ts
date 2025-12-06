import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderDetalleInventarioLotesComponent } from "../../components/header-detalle-inventario-lotes/header-detalle-inventario-lotes.component";
import { DetalleMovimientoInventarioLoteComponent } from "../../components/detalle-movimiento-inventario-lote/detalle-movimiento-inventario-lote.component";

@Component({
  selector: 'app-ver-detalle-invnetario-lotes-pages',
  imports: [NavbarBodegaComponent, HeaderDetalleInventarioLotesComponent, DetalleMovimientoInventarioLoteComponent],
  templateUrl: './ver-detalle-invnetario-lotes-pages.component.html',
  styleUrl: './ver-detalle-invnetario-lotes-pages.component.css'
})
export class VerDetalleInvnetarioLotesPagesComponent implements OnInit {

  idMovimiento!: string;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.idMovimiento = params.get('id')!;
    });
  }
}
