import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderMermaInventarioComponent } from './header-merma-inventario.component';

describe('HeaderMermaInventarioComponent', () => {
  let component: HeaderMermaInventarioComponent;
  let fixture: ComponentFixture<HeaderMermaInventarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderMermaInventarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderMermaInventarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
