import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { Cargos } from '../models/dropdowns/cargos';

@Injectable({
  providedIn: 'root'
})
export class CargosService{

  private readonly resource = `${api}/cargos`;


  constructor(private client: HttpClient) {}

  public getCargos(): Observable<Array<Cargos>> {
    return this.client.get<Array<Cargos>>(`${this.resource}`);
  }

}
