import {Router} from '@angular/router';
import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProductoCardComponent} from '../producto-card/producto-card.component';
import {ProductoVentas} from '../../models/producto-ventas';
import {HttpClient} from '@angular/common/http';

interface ProductoPaginado {
  content: ProductoVentas[];
  pageable: any;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  sort: any;
  numberOfElements: number;
  empty: boolean;
}

@Component({
  selector: 'app-productos-tabla-vendedor',
  standalone: true,
  imports: [CommonModule, ProductoCardComponent],
  templateUrl: './productos-tabla-vendedor.component.html',
  styleUrls: ['./productos-tabla-vendedor.component.css']
})
export class ProductosTablaComponent implements OnInit {

  productos: ProductoVentas[] = [];

  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit(): void {
    this.http.get<ProductoPaginado>('http://localhost:8080/productos/buscar')
      .subscribe({
        next: (data) =>
          this.productos = data.content.sort((a, b) => b.stockTotal - a.stockTotal),

        error: err => console.error('Error cargando productos', err)
      });
  }

  agregarProducto(producto: ProductoVentas) {
    console.log("Producto agregado:", producto);

    // 👇 Aquí rediriges al detalle con un parámetro
    this.router.navigate(['/detalle-venta-vendedor'], {
      state: {producto}
    });
  }
}
