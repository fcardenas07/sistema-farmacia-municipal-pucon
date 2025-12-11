import { Component } from '@angular/core';
import { NavbarBodegaComponent } from "../../../../core/navbar-bodega/navbar-bodega.component";
import { HeaderDashboardComponent } from "../../components/header-dashboard/header-dashboard.component";
import { DashboardBodegaComponent } from "../../components/dashboard-bodega/dashboard-bodega.component";

@Component({
  selector: 'app-dashboard-bodega-page',
  imports: [NavbarBodegaComponent, HeaderDashboardComponent, DashboardBodegaComponent],
  templateUrl: './dashboard-bodega-page.component.html',
  styleUrl: './dashboard-bodega-page.component.css'
})
export class DashboardBodegaPageComponent {

}
