import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PesquisarAtendimentoComponent } from './pesquisar-atendimento.component';

describe('PesquisarAtendimentoComponent', () => {
  let component: PesquisarAtendimentoComponent;
  let fixture: ComponentFixture<PesquisarAtendimentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PesquisarAtendimentoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PesquisarAtendimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
