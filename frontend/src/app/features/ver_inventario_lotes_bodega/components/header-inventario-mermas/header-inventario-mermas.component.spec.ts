import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderInventarioMermasComponent } from './header-inventario-mermas.component';

describe('HeaderInventarioMermasComponent', () => {
  let component: HeaderInventarioMermasComponent;
  let fixture: ComponentFixture<HeaderInventarioMermasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderInventarioMermasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderInventarioMermasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
