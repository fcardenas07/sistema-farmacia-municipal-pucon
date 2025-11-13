import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { DetalleVentaVendedorComponent } from './features/ventas_facturacion_boletas/pages/detalle-venta-vendedor/detalle-venta-vendedor.component';
export const routes: Routes = [
    {path: 'login', component: LoginPageComponent},
    {path: 'detalle-venta-vendedor', component: DetalleVentaVendedorComponent}, 
    {path: '**', redirectTo: 'login' }
];
