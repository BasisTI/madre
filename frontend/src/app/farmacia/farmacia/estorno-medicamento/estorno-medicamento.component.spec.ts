import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EstornoMedicamentoComponent } from './estorno-medicamento.component';

describe('EstornoMedicamentoComponent', () => {
  let component: EstornoMedicamentoComponent;
  let fixture: ComponentFixture<EstornoMedicamentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EstornoMedicamentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EstornoMedicamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
