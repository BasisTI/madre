import { TestBed } from '@angular/core/testing';

import { TipoDeUnidadeFuncionalService } from './tipo-de-unidade-funcional.service';

describe('TipoDeUnidadeFuncionalService', () => {
  let service: TipoDeUnidadeFuncionalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TipoDeUnidadeFuncionalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
