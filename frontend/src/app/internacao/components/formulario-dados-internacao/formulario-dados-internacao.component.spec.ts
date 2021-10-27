import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioDadosInternacaoComponent } from './formulario-dados-internacao.component';

describe('FormularioDadosInternacaoComponent', () => {
  let component: FormularioDadosInternacaoComponent;
  let fixture: ComponentFixture<FormularioDadosInternacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormularioDadosInternacaoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormularioDadosInternacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
