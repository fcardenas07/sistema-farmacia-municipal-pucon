import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaProductosQfComponent } from './lista-productos-qf.component';

describe('ListaProductosQfComponent', () => {
  let component: ListaProductosQfComponent;
  let fixture: ComponentFixture<ListaProductosQfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaProductosQfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaProductosQfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
