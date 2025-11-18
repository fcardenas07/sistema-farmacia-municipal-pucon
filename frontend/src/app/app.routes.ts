import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { DetalleVentaVendedorComponent } from './features/ventas_facturacion_boletas/pages/detalle-venta-vendedor/detalle-venta-vendedor.component';
import { ProductosVendedorPageComponent } from './features/ventas_facturacion_boletas/pages/productos-vendedor/productos-vendedor.component';
import { BodegaHomeComponent } from './features/home/pages/bodega-home/bodega-home.component';
import { IngresoCodigoPedidoPageComponent } from './features/recepcion-productos-bodega/pages/ingreso-codigo-pedido-page/ingreso-codigo-pedido-page.component';
import { AgregarStockPedidoPageComponent } from './features/recepcion-productos-bodega/pages/agregar-stock-pedido-page/agregar-stock-pedido-page.component';
export const routes: Routes = [
    {path: 'login', component: LoginPageComponent},
    {path: 'detalle-venta-vendedor', component: DetalleVentaVendedorComponent},
    {path: 'productos-vendedor', component: ProductosVendedorPageComponent},
    {path: 'Home-bodega', component: BodegaHomeComponent},
    {path: 'ingreso-codigo-pedido',component: IngresoCodigoPedidoPageComponent},
    {path: 'agregar-stock-pedido',component: AgregarStockPedidoPageComponent},
    {path: '**', redirectTo: 'login' }
];
