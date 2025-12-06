import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderMermaInventarioComponent } from "../../components/header-merma-inventario/header-merma-inventario.component";
import { DetalleMovimientoInventarioMermasComponent } from "../../components/detalle-movimiento-inventario-mermas/detalle-movimiento-inventario-mermas.component";

@Component({
  selector: 'app-ver-detalle-inventario-mermas-pages',
  standalone: true,
  imports: [
    NavbarBodegaComponent,
    HeaderMermaInventarioComponent,
    DetalleMovimientoInventarioMermasComponent  
  ],
  templateUrl: './ver-detalle-inventario-mermas-pages.component.html',
  styleUrl: './ver-detalle-inventario-mermas-pages.component.css'
})
export class VerDetalleInventarioMermasPagesComponent implements OnInit {

  idMovimiento!: string;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.idMovimiento = params.get('id')!;
    });
  }

}
