import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaExamesComponent } from './tabela-exames.component';

describe('TabelaExamesComponent', () => {
  let component: TabelaExamesComponent;
  let fixture: ComponentFixture<TabelaExamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabelaExamesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TabelaExamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
