import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescricaoMedicaComponent } from './prescricao-medica.component';

describe('PrescricaoMedicaComponent', () => {
  let component: PrescricaoMedicaComponent;
  let fixture: ComponentFixture<PrescricaoMedicaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrescricaoMedicaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrescricaoMedicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
