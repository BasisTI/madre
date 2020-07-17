import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnosticoComponent } from './diagnostico.component';

describe('DiagnosticoComponent', () => {
  let component: DiagnosticoComponent;
  let fixture: ComponentFixture<DiagnosticoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnosticoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosticoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
