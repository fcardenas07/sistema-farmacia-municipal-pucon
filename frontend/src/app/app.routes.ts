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
import { QfHomeComponent } from './features/home/pages/qf-home/qf-home.component';
import { ProductosQfPageComponent } from './features/crear_productos_QF/pages/productos-qf-page/productos-qf-page.component';
import { EditarProductosComponent } from './features/crear_productos_QF/pages/editar-producto/editar-producto.component';

import { StockCriticoQfPageComponent } from './features/ver_stock_QF/pages/stock-critico-qf-page/stock-critico-qf-page.component';
import { StockNormalQfPageComponent } from './features/ver_stock_QF/pages/stock-normal-qf-page/stock-normal-qf-page.component';
export const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'detalle-venta-vendedor', component: DetalleVentaVendedorComponent },
  { path: 'productos-vendedor', component: ProductosVendedorPageComponent },
  { path: 'home-bodega', component: BodegaHomeComponent },
  { path: 'home-qf', component: QfHomeComponent },
  { path: 'productos-qf', component: ProductosQfPageComponent },
  {
    path: 'ingreso-codigo-pedido',
    component: IngresoCodigoPedidoPageComponent,
  },
  { path: 'agregar-stock-pedido', component: AgregarStockPedidoPageComponent },
  { path: 'resumen-pedido', component: ResumenPedidoPageComponent },
  { path: 'stock-critico', component: StockCriticoPageComponent },
  { path: 'stock-total', component: StockTotalPageComponent },
  { path: 'crear-productos', component: CrearProductosPageComponent },
  { path: 'editar-producto/:id', component: EditarProductosComponent },
  {
    path: 'resumen-creacion-producto',
    component: ResumenCreacionProductoPageComponent,
  },
  { path: '**', redirectTo: 'login' },
  { path: 'login', component: LoginPageComponent },
  { path: 'detalle-venta-vendedor', component: DetalleVentaVendedorComponent },
  { path: 'productos-vendedor', component: ProductosVendedorPageComponent },
  { path: 'home-bodega', component: BodegaHomeComponent },
  { path: 'home-qf', component: QfHomeComponent },
  {
    path: 'ingreso-codigo-pedido',
    component: IngresoCodigoPedidoPageComponent,
  },
  { path: 'agregar-stock-pedido', component: AgregarStockPedidoPageComponent },
  { path: 'resumen-pedido', component: ResumenPedidoPageComponent },
  { path: 'stock-critico', component: StockCriticoPageComponent },
  { path: 'stock-total', component: StockTotalPageComponent },
  { path: 'crear-productos', component: CrearProductosPageComponent },
  {
    path: 'resumen-creacion-producto',
    component: ResumenCreacionProductoPageComponent,
  },
  { path: 'stock-critico-qf', component: StockCriticoQfPageComponent },
  { path: 'stock-normal-qf', component: StockNormalQfPageComponent },
  { path: '**', redirectTo: 'login' },
];
