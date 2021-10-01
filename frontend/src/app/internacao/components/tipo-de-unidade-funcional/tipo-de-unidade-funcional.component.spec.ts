import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoDeUnidadeFuncionalComponent } from './tipo-de-unidade-funcional.component';

describe('TipoDeUnidadeFuncionalComponent', () => {
  let component: TipoDeUnidadeFuncionalComponent;
  let fixture: ComponentFixture<TipoDeUnidadeFuncionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TipoDeUnidadeFuncionalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TipoDeUnidadeFuncionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
