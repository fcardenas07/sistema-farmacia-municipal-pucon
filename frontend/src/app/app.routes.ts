import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { DetalleVentaVendedorComponent } from './features/ventas_facturacion_boletas/pages/detalle-venta-vendedor/detalle-venta-vendedor.component';
import { ProductosVendedorPageComponent } from './features/ventas_facturacion_boletas/pages/productos-vendedor/productos-vendedor.component';
export const routes: Routes = [
    {path: 'login', component: LoginPageComponent},
    {path: 'detalle-venta-vendedor', component: DetalleVentaVendedorComponent},
    {path: 'productos-vendedor', component: ProductosVendedorPageComponent},
    {path: '**', redirectTo: 'login' }
];
