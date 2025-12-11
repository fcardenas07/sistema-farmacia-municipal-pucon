import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerInventarioLotesPageComponent } from './ver-inventario-lotes-page.component';

describe('VerInventarioLotesPageComponent', () => {
  let component: VerInventarioLotesPageComponent;
  let fixture: ComponentFixture<VerInventarioLotesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerInventarioLotesPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerInventarioLotesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
