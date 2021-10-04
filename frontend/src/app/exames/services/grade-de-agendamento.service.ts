import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { GradeDeAgendamentoExame } from '../models/subjects/grades-de-agendamento';
import { Sala } from '../models/subjects/sala';
import { TipoDeMarcacao } from '../models/subjects/tipo-de-marcacao';
import { HorarioAgendado } from '../models/subjects/horario-agendado';

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
    grupoAgendamentoExameId: string,
    exameGradeId: string,
    responsavelId: string
  ): Observable<GradeDeAgendamentoExame[]> {
    return this.client.get<GradeDeAgendamentoExame[]>(`${this.URL}/_search/grades-de-agendamento`, {
      params: new HttpParams()
        .set('id', id)
        .set('unidadeExecutoraId', unidadeExecutoraId)
        .set('ativo', ativo)
        .set('grupoAgendamentoExameId', grupoAgendamentoExameId)
        .set('exameGradeId', exameGradeId)
        .set('responsavelId', responsavelId)
    });
  }

  public getSalas(): Observable<Array<Sala>> {
    return this.client.get<Array<Sala>>(`${this.URL}/salas`);
  }

  public getSalasPorUnidade(unidadeExecutoraId: string, ativo: string): Observable<Array<Sala>> {
    return this.client.get<Array<Sala>>(`${this.URL}/_search/salas-por-unidade`, {
      params: new HttpParams()
        .set('unidadeExecutoraId', unidadeExecutoraId)
        .set('ativo', ativo)
    });
  }

  public getTiposDeMarcacao(): Observable<Array<TipoDeMarcacao>> {
    return this.client.get<Array<TipoDeMarcacao>>(`${this.URL}/tipo-de-marcacaos`);
  }

  public getHorariosAgendados(): Observable<Array<HorarioAgendado>> {
    return this.client.get<Array<HorarioAgendado>>(`${this.URL}/_search/horarios-agendados`);
  }

  cadastrarGrade(gradeDeAgendamento: GradeDeAgendamentoExame) {
    return this.client.post(`${this.URL}/grade-agendamento-exames`, gradeDeAgendamento);
  }

  cadastrarHorarioGrade(horarioGrade: HorarioAgendado) {
    return this.client.post(`${this.URL}/horario-agendados`, horarioGrade);
  }

}
