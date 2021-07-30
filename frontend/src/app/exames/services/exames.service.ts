import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ExamModel } from "../models/subjects/exames-model";

@Injectable({
  providedIn: 'root'
})
export class ExamesService {

  private readonly URL = '/madreexames/api'

  constructor(
    private client: HttpClient
  ) { }

  // Pegar todos os exames
  public GetExames(): Observable<Array<ExamModel>> {
    return this.client.get<Array<ExamModel>>(`${this.URL}/exames`);
  }

  // Pegar os exames pelo ID do grupo
  public GetExamesPorGrupo(id: number): Observable<Array<ExamModel>> {
    return this.client.get<Array<ExamModel>>(`${this.URL}/exames/grupos/${id}`);
  }
  
  // Pegar os exames pelo ID
  public GetExamesPorId(id: number): Observable<ExamModel> {
    return this.client.get<ExamModel>(`${this.URL}/exames/${id}`);
  }
}
