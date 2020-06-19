import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DispensacaoMedicamentoComponent } from './dispensacao-medicamento.component';

describe('DispensacaoMedicamentoComponent', () => {
  let component: DispensacaoMedicamentoComponent;
  let fixture: ComponentFixture<DispensacaoMedicamentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DispensacaoMedicamentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DispensacaoMedicamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
