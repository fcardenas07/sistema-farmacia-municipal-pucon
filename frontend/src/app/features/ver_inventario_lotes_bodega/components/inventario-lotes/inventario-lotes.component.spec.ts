import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventarioLotesComponent } from './inventario-lotes.component';

describe('InventarioLotesComponent', () => {
  let component: InventarioLotesComponent;
  let fixture: ComponentFixture<InventarioLotesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventarioLotesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventarioLotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
