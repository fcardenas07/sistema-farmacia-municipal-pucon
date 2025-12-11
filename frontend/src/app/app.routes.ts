import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { DetalleVentaVendedorComponent } from './features/ventas_facturacion_boletas/pages/detalle-venta-vendedor/detalle-venta-vendedor.component';
import { ProductosVendedorPageComponent } from './features/ventas_facturacion_boletas/pages/productos-vendedor/productos-vendedor.component';
import { BodegaHomeComponent } from './features/home/pages/bodega-home/bodega-home.component';
import { IngresoCodigoPedidoPageComponent } from './features/recepcion-productos-bodega/pages/ingreso-codigo-pedido-page/ingreso-codigo-pedido-page.component';
import { AgregarStockPedidoPageComponent } from './features/recepcion-productos-bodega/pages/agregar-stock-pedido-page/agregar-stock-pedido-page.component';
import { ResumenPedidoPageComponent } from './features/recepcion-productos-bodega/pages/resumen-pedido-page/resumen-pedido-page.component';
import { StockCriticoPageComponent } from './features/ver_stock_bodega/pages/stock-critico-page/stock-critico-page.component';
import { StockTotalPageComponent } from './features/ver_stock_bodega/pages/stock-total-page/stock-total-page.component';
import { CrearProductosPageComponent } from './features/crear_productos_QF/pages/crear-productos-page/crear-productos-page.component';
import { ResumenCreacionProductoPageComponent } from './features/crear_productos_QF/pages/resumen-creacion-producto-page/resumen-creacion-producto-page.component';
import { VerInventarioLotesPageComponent } from './features/ver_inventario_lotes_bodega/pages/ver-inventario-lotes-page/ver-inventario-lotes-page.component';
import { FormularioMermaPageComponent } from './features/ver_inventario_lotes_bodega/pages/formulario-merma-page/formulario-merma-page.component';
import { VerInvnetarioMermasPageComponent } from './features/ver_inventario_lotes_bodega/pages/ver-invnetario-mermas-page/ver-invnetario-mermas-page.component';
import { DashboardBodegaPageComponent } from './features/ver_inventario_lotes_bodega/pages/dashboard-bodega-page/dashboard-bodega-page.component';
import { VerDetalleInvnetarioLotesPagesComponent } from './features/ver_inventario_lotes_bodega/pages/ver-detalle-invnetario-lotes-pages/ver-detalle-invnetario-lotes-pages.component';
import { VerDetalleInventarioMermasPagesComponent } from './features/ver_inventario_lotes_bodega/pages/ver-detalle-inventario-mermas-pages/ver-detalle-inventario-mermas-pages.component';
import { QfHomeComponent } from './features/home/pages/qf-home/qf-home.component';
import { ProductosQfPageComponent } from './features/crear_productos_QF/pages/productos-qf-page/productos-qf-page.component';
import { EditarProductosComponent } from './features/crear_productos_QF/pages/editar-producto/editar-producto.component';
import { StockCriticoQfPageComponent } from './features/ver_stock_QF/pages/stock-critico-qf-page/stock-critico-qf-page.component';
import { StockNormalQfPageComponent } from './features/ver_stock_QF/pages/stock-normal-qf-page/stock-normal-qf-page.component';
import { AuthGuard } from './features/auth/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginPageComponent },

  // Rutas para VENDEDOR
  {
    path: 'detalle-venta-vendedor',
    component: DetalleVentaVendedorComponent,
    canActivate: [AuthGuard],
    data: { roles: ['VENDEDOR'] },
  },
  {
    path: 'productos-vendedor',
    component: ProductosVendedorPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['VENDEDOR'] },
  },

  // Rutas para BODEGUERO
  {
    path: 'home-bodega',
    component: BodegaHomeComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'ingreso-codigo-pedido',
    component: IngresoCodigoPedidoPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'agregar-stock-pedido',
    component: AgregarStockPedidoPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'resumen-pedido',
    component: ResumenPedidoPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'stock-critico',
    component: StockCriticoPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'stock-total',
    component: StockTotalPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'inventario-lotes-bodega',
    component: VerInventarioLotesPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'formularo-merma-inventario',
    component: FormularioMermaPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'mermas-inventario',
    component: VerInvnetarioMermasPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'dashboard-bodega',
    component: DashboardBodegaPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'detalle-movimiento/:id',
    component: VerDetalleInvnetarioLotesPagesComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },
  {
    path: 'detalle-movimiento-merma/:id',
    component: VerDetalleInventarioMermasPagesComponent,
    canActivate: [AuthGuard],
    data: { roles: ['BODEGUERO'] },
  },

  // Rutas para QF (Químico Farmacéutico)
  {
    path: 'home-qf',
    component: QfHomeComponent,
    canActivate: [AuthGuard],
    data: { roles: ['QF'] },
  },
  {
    path: 'productos-qf',
    component: ProductosQfPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['QF'] },
  },
  {
    path: 'crear-productos',
    component: CrearProductosPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['QF'] },
  },
  {
    path: 'editar-producto/:id',
    component: EditarProductosComponent,
    canActivate: [AuthGuard],
    data: { roles: ['QF'] },
  },
  {
    path: 'resumen-creacion-producto',
    component: ResumenCreacionProductoPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['QF'] },
  },
  {
    path: 'stock-critico-qf',
    component: StockCriticoQfPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['QF'] },
  },
  {
    path: 'stock-normal-qf',
    component: StockNormalQfPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['QF'] },
  },

  // Redirecciones
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];
