import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { GradesDeAgendamento } from '../models/subjects/grades-de-agendamento';
import { Sala } from '../models/subjects/sala';

@Injectable({
  providedIn: 'root'
})
export class GradeDeAgendamentoService {

  private readonly URL = '/madreexames/api'

  constructor(
    private client: HttpClient
  ) { }

  public getGradesDeAgendamento(
    id: string,
    unidadeExecutoraId: string,
    ativo: string,
    salaGradeId: string,
    grupoAgendamentoExameId: string,
    exameGradeId: string,
    responsavelId: string
  ): Observable<GradesDeAgendamento[]> {
    return this.client.get<GradesDeAgendamento[]>(`${this.URL}/_search/grades-de-agendamento`, {
      params: new HttpParams()
        .set('id', id)
        .set('unidadeExecutoraId', unidadeExecutoraId)
        .set('ativo', ativo)
        .set('salaGradeId', salaGradeId)
        .set('grupoAgendamentoExameId', grupoAgendamentoExameId)
        .set('exameGradeId', exameGradeId)
        .set('responsavelId', responsavelId)
    });
  }

  public getSalas(): Observable<Array<Sala>> {
    return this.client.get<Array<Sala>>(`${this.URL}/salas`);
  }

  cadastrarGrade(gradeDeAgendamento: GradesDeAgendamento) {
    return this.client.post(`${this.URL}/grade-de-agendamentos`, gradeDeAgendamento);
  }

}
