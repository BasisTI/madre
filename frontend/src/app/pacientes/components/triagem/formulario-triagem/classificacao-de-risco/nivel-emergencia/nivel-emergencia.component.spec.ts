import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NivelEmergenciaComponent } from './nivel-emergencia.component';

describe('NivelEmergenciaComponent', () => {
  let component: NivelEmergenciaComponent;
  let fixture: ComponentFixture<NivelEmergenciaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NivelEmergenciaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NivelEmergenciaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
