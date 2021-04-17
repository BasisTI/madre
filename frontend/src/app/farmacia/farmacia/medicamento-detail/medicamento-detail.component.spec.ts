import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicamentoDetailComponent } from './medicamento-detail.component';

describe('MedicamentoDetailComponent', () => {
  let component: MedicamentoDetailComponent;
  let fixture: ComponentFixture<MedicamentoDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicamentoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicamentoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
