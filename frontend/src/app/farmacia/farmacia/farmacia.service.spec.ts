import { TestBed } from '@angular/core/testing';

import { FarmaciaService } from './farmacia.service';

describe('FarmaciaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FarmaciaService = TestBed.get(FarmaciaService);
    expect(service).toBeTruthy();
  });
});
