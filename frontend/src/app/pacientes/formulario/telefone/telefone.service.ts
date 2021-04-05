import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DDD } from '../../models/dropdowns/types/DDD';

@Injectable({
  providedIn: 'root'
})
export class TelefoneService {

  uriServico: string = '/pacientes/api/list/ddd';

  constructor(private http: HttpClient) { }

  getListaDeDDDs(): Observable<DDD[]>{
    return this.http.get<DDD[]>(this.uriServico);
  }
}
