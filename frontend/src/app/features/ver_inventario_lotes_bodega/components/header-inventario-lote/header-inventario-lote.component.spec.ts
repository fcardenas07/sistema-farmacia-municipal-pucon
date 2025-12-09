import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderInventarioLoteComponent } from './header-inventario-lote.component';

describe('HeaderInventarioLoteComponent', () => {
  let component: HeaderInventarioLoteComponent;
  let fixture: ComponentFixture<HeaderInventarioLoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderInventarioLoteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderInventarioLoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
