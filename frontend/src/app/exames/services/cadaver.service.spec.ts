import { TestBed } from '@angular/core/testing';

import { CadaverService } from './cadaver.service';

describe('CadaverService', () => {
  let service: CadaverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CadaverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
