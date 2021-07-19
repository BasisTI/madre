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

  public GetExames(): Observable<Array<ExamModel>> {
    return this.client.get<Array<ExamModel>>(`${this.URL}/exames`);
  }
}
