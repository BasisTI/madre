import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ControleQualidadeModel } from '../models/subjects/controleQualidade-model';

@Injectable({
  providedIn: 'root'
})
export class ControleQualidadeservice {

  private readonly grupoURL = '/madreexames/api'

  constructor(
    private client: HttpClient
  ) { }
  public getControleQualidade(): Observable<Array<ControleQualidadeModel>> {
    return this.client.get<Array<ControleQualidadeModel>>(`${this.grupoURL}/controle-qualidades`);
  }
}
