import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { GrupoModel } from "../models/subjects/grupo-model";

@Injectable({
  providedIn: 'root'
})
export class ResponsavelService {

  private readonly apiUrl = 'pacientes/api/responsavels';

  constructor(
    private client: HttpClient
  ) { }

  public getResponsavel(): Observable<Array<GrupoModel>> {
    return this.client.get<Array<GrupoModel>>(this.apiUrl);
  }
}
