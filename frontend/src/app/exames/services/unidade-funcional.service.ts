import { Injectable } from '@angular/core';
import { GrupoModel } from "../models/subjects/grupo-model";
import { HttpClient, HttpParams } from '@angular/common/http';
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

  public GetUnidades(): Observable<Array<GrupoModel>> {
    return this.client.get<Array<GrupoModel>>(this.unidadesUrl);
  }

}
