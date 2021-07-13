import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitarExameComponent } from './solicitar-exame.component';

describe('SolicitarExameComponent', () => {
  let component: SolicitarExameComponent;
  let fixture: ComponentFixture<SolicitarExameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SolicitarExameComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SolicitarExameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
