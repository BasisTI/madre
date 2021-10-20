import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { GradeDeAgendamentoExame } from '../models/subjects/grades-de-agendamento';
import { Sala } from '../models/subjects/sala';
import { TipoDeMarcacao } from '../models/subjects/tipo-de-marcacao';
import { HorarioExame } from '../models/subjects/horario-agendado';
import { Dia } from '../models/subjects/dia';

@Injectable({
    providedIn: 'root',
})
export class GradeDeAgendamentoService {
    private readonly URL = '/madreexames/api';

    constructor(private client: HttpClient) {}

    public getGradesDeAgendamento(
        id: string,
        unidadeExecutoraId: string,
        ativo: string,
        duracao: string,
        responsavelId: string,
        exameId: string,
        salaId: string,
    ): Observable<GradeDeAgendamentoExame[]> {
        return this.client.get<GradeDeAgendamentoExame[]>(
            `${this.URL}/_search/grades-de-agendamento`,
            {
                params: new HttpParams()
                    .set('id', id)
                    .set('unidadeExecutoraId', unidadeExecutoraId)
                    .set('ativo', ativo)
                    .set('duracao', duracao)
                    .set('responsavelId', responsavelId)
                    .set('exameId', exameId)
                    .set('salaId', salaId),
            },
        );
    }

    public getSalas(): Observable<Array<Sala>> {
        return this.client.get<Array<Sala>>(`${this.URL}/salas`);
    }

    public getTiposDeMarcacao(): Observable<Array<TipoDeMarcacao>> {
        return this.client.get<Array<TipoDeMarcacao>>(`${this.URL}/tipo-de-marcacaos`);
    }
    public getDias(): Observable<Dia[]> {
        return this.client.get<Dia[]>(`${this.URL}/dias`);
    }

    public getHorariosExame(gradeAgendamentoExameId: string): Observable<Array<HorarioExame>> {
        return this.client.get<Array<HorarioExame>>(`${this.URL}/_search/horarios-exame`, {
            params: new HttpParams().set('gradeAgendamentoExameId', gradeAgendamentoExameId),
        });
    }

    cadastrarGrade(gradeDeAgendamento: GradeDeAgendamentoExame) {
        return this.client.post(`${this.URL}/grade-agendamento-exames`, gradeDeAgendamento);
    }

    cadastrarHorarioGrade(horarioGrade: HorarioExame) {
        return this.client.post(`${this.URL}/horario-agendados`, horarioGrade);
    }
}
