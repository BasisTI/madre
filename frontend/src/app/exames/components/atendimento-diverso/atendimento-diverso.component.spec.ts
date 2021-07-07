import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AtendimentoDiversoComponent } from './atendimento-diverso.component';

describe('AtendimentoDiversoComponent', () => {
  let component: AtendimentoDiversoComponent;
  let fixture: ComponentFixture<AtendimentoDiversoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AtendimentoDiversoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AtendimentoDiversoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
