import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';
import { UnidadeFuncional } from '../models/subjects/unidade-funcional-model';


@Injectable({
  providedIn: 'root'
})

export class UnidadeFuncionalService {

  private readonly unidadesUrl = `${api}/unidades-funcionais`;

  constructor(
    private client: HttpClient
  ) { }

  public getUnidades(): Observable<Array<UnidadeFuncional>> {
    return this.client.get<Array<UnidadeFuncional>>(this.unidadesUrl);
  }

}
