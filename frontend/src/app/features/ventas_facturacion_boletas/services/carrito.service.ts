import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {ProductoVentas} from '../models/producto-ventas';

export interface ProductoCarrito extends ProductoVentas {
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private carritoSubject = new BehaviorSubject<ProductoCarrito[]>([]);
  carrito$ = this.carritoSubject.asObservable();

  // Agregar producto al carrito
  agregarProducto(producto: ProductoVentas, precio: number, cantidad: number = 1): void {
    const carritoActual = this.carritoSubject.value;

    // Verificar si el producto ya está en el carrito
    const productoExistente = carritoActual.find(item => item.idProducto === producto.idProducto);

    if (productoExistente) {
      // Actualizar cantidad si ya existe
      productoExistente.cantidad += cantidad;
      productoExistente.subtotal = productoExistente.cantidad * productoExistente.precioUnitario;
    } else {
      // Agregar nuevo producto al carrito
      const nuevoProducto: ProductoCarrito = {
        ...producto,
        cantidad: cantidad,
        precioUnitario: precio,
        subtotal: cantidad * precio
      };
      carritoActual.push(nuevoProducto);
    }

    this.carritoSubject.next([...carritoActual]);
  }

  // Remover producto del carrito
  removerProducto(idProducto: string): void {
    const carritoActual = this.carritoSubject.value.filter(item => item.idProducto !== idProducto);
    this.carritoSubject.next(carritoActual);
  }

  // Actualizar cantidad de un producto
  actualizarCantidad(idProducto: string, nuevaCantidad: number): void {
    const carritoActual = this.carritoSubject.value.map(item => {
      if (item.idProducto === idProducto) {
        return {
          ...item,
          cantidad: nuevaCantidad,
          subtotal: nuevaCantidad * item.precioUnitario
        };
      }
      return item;
    });
    this.carritoSubject.next(carritoActual);
  }

  // Obtener carrito actual
  getCarrito(): ProductoCarrito[] {
    return this.carritoSubject.value;
  }

  // Calcular total del carrito
  getTotal(): number {
    return this.carritoSubject.value.reduce((total, item) => total + item.subtotal, 0);
  }

  // Limpiar carrito
  limpiarCarrito(): void {
    this.carritoSubject.next([]);
  }

  // Obtener cantidad total de productos
  getCantidadTotal(): number {
    return this.carritoSubject.value.reduce((total, item) => total + item.cantidad, 0);
  }
}
