import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DispensacaoDetailComponent } from './dispensacao-detail.component';

describe('DispensacaoDetailComponent', () => {
  let component: DispensacaoDetailComponent;
  let fixture: ComponentFixture<DispensacaoDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DispensacaoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DispensacaoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
