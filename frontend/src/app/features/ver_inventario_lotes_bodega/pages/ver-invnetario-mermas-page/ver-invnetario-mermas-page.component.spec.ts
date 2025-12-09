import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerInvnetarioMermasPageComponent } from './ver-invnetario-mermas-page.component';

describe('VerInvnetarioMermasPageComponent', () => {
  let component: VerInvnetarioMermasPageComponent;
  let fixture: ComponentFixture<VerInvnetarioMermasPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerInvnetarioMermasPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerInvnetarioMermasPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
