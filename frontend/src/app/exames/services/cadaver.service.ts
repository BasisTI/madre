import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CadaverModel } from "../models/subjects/cadaver-model";


@Injectable({
  providedIn: 'root'
})
export class CadaverService {

  private readonly grupoURL = '/madreexames/api'

  constructor(
    private client: HttpClient
  ) { }
  public GetCadaver(): Observable<Array<CadaverModel>> {
    return this.client.get<Array<CadaverModel>>(`${this.grupoURL}/cadavers`);
  }
}
