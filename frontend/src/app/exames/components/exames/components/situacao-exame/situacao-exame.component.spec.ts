import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SituacaoExameComponent } from './situacao-exame.component';

describe('SituacaoExameComponent', () => {
  let component: SituacaoExameComponent;
  let fixture: ComponentFixture<SituacaoExameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SituacaoExameComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SituacaoExameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
