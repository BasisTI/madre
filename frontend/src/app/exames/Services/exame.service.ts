import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ExamModel } from '../models/subjects/exames-model';

@Injectable({
    providedIn: 'root',
})
export class ExameService {
    private readonly pacUrl = 'exames/api';

    constructor(private client: HttpClient) { }

    public buscarPaciente(): Observable<Array<ExamModel>> {
        return this.client.get<Array<ExamModel>>(`${this.pacUrl}/exames`);
    }
}
