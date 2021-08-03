import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';
import { UnidadeFuncional } from '../models/subjects/unidade-funcional-model';

@Injectable({
  providedIn: 'root'
})
export class UnidadeFuncionalService{

  private readonly resource = `${api}/unidades-funcionais`;

  constructor(private client: HttpClient) {}

  public getUnidadeFuncional(): Observable<Array<UnidadeFuncional>> {
    return this.client.get<Array<UnidadeFuncional>>(`${this.resource}`);
  }

}
