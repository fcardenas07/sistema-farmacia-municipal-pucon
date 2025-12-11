import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventarioMermasComponent } from './inventario-mermas.component';

describe('InventarioMermasComponent', () => {
  let component: InventarioMermasComponent;
  let fixture: ComponentFixture<InventarioMermasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventarioMermasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventarioMermasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
