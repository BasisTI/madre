import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
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

  public getGradesDeAgendamento(): Observable<Array<GradesDeAgendamento>> {
    return this.client.get<Array<GradesDeAgendamento>>(`${this.URL}/grade-de-agendamentos`);
  }

  public getSalas(): Observable<Array<Sala>> {
    return this.client.get<Array<Sala>>(`${this.URL}/salas`);
  }

  cadastrarGrade(gradeDeAgendamento: GradesDeAgendamento){
    return this.client.post(`${this.URL}/grade-de-agendamentos`, gradeDeAgendamento);
  } 

}
