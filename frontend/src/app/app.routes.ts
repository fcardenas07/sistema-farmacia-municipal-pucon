import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { DetalleVentaVendedorComponent } from './features/ventas_facturacion_boletas/pages/detalle-venta-vendedor/detalle-venta-vendedor.component';
import { ProductosVendedorPageComponent } from './features/ventas_facturacion_boletas/pages/productos-vendedor/productos-vendedor.component';
import { BodegaHomeComponent } from './features/home/pages/bodega-home/bodega-home.component';
import { IngresoCodigoPedidoPageComponent } from './features/recepcion-productos-bodega/pages/ingreso-codigo-pedido-page/ingreso-codigo-pedido-page.component';
import { AgregarStockPedidoPageComponent } from './features/recepcion-productos-bodega/pages/agregar-stock-pedido-page/agregar-stock-pedido-page.component';
import { ResumenPedidoPageComponent } from './features/recepcion-productos-bodega/pages/resumen-pedido-page/resumen-pedido-page.component';
import { CrearProductosPageComponent } from './features/crear_productos_QF/pages/crear-productos-page/crear-productos-page.component';
import { ResumenCreacionProductoPageComponent } from './features/crear_productos_QF/pages/resumen-creacion-producto-page/resumen-creacion-producto-page.component';
import { VerInventarioLotesPageComponent } from './features/ver_inventario_lotes_bodega/pages/ver-inventario-lotes-page/ver-inventario-lotes-page.component';
import { FormularioMermaPageComponent } from './features/ver_inventario_lotes_bodega/pages/formulario-merma-page/formulario-merma-page.component';
import { VerInvnetarioMermasPageComponent } from './features/ver_inventario_lotes_bodega/pages/ver-invnetario-mermas-page/ver-invnetario-mermas-page.component';
import { DashboardBodegaPageComponent } from './features/ver_inventario_lotes_bodega/pages/dashboard-bodega-page/dashboard-bodega-page.component';
import { VerDetalleInvnetarioLotesPagesComponent } from './features/ver_inventario_lotes_bodega/pages/ver-detalle-invnetario-lotes-pages/ver-detalle-invnetario-lotes-pages.component';
export const routes: Routes = [
    {path: 'login', component: LoginPageComponent},
    {path: 'detalle-venta-vendedor', component: DetalleVentaVendedorComponent},
    {path: 'productos-vendedor', component: ProductosVendedorPageComponent},
    {path: 'home-bodega', component: BodegaHomeComponent},
    {path: 'ingreso-codigo-pedido',component: IngresoCodigoPedidoPageComponent},
    {path: 'agregar-stock-pedido',component: AgregarStockPedidoPageComponent},
    {path: 'resumen-pedido',component: ResumenPedidoPageComponent},
    {path: 'crear-productos',component: CrearProductosPageComponent},
    {path: 'resumen-creacion-producto',component: ResumenCreacionProductoPageComponent},
    {path: 'inventario-lotes-bodega',component: VerInventarioLotesPageComponent},
    {path: 'formularo-merma-inventario',component: FormularioMermaPageComponent},
    {path: 'mermas-inventario',component: VerInvnetarioMermasPageComponent},
    {path: 'dashboard-bodega',component: DashboardBodegaPageComponent},
    {path: 'detalle-movimiento/:id',component: VerDetalleInvnetarioLotesPagesComponent},
    {path: '**', redirectTo: 'login' },
  

];
