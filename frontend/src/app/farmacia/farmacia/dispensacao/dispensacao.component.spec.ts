import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DispensacaoComponent } from './dispensacao.component';

describe('DispensacaoComponent', () => {
  let component: DispensacaoComponent;
  let fixture: ComponentFixture<DispensacaoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DispensacaoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DispensacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
