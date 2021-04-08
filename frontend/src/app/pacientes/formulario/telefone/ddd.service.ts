import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DDD } from '../../models/dropdowns/types/DDD';

@Injectable({
  providedIn: 'root'
})
export class DDDService extends CrudServiceNuvem<number, DDD>{

  uriServico: string = '/pacientes/api/list/ddd';

  constructor(private httpClient: HttpClient) {
    super('/pacientes/api/list/ddd', httpClient);
  }
  
}
