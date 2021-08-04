import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class CentroAtividadeService {
    private readonly resource = `${api}/centros-de-atividade`;

    constructor(private client: HttpClient) {}

    public getCentrosPorDescricao(descricao: string): Observable<CentroAtividade[]> {
        return this.client.get<CentroAtividade[]>(this.resource, {
            params: new HttpParams().set('sort', 'descricao').set('descricao', descricao),
        });
    }

    public getCentros(): Observable<Array<CentroAtividade>> {
        return this.client.get<Array<CentroAtividade>>(`${this.resource}`);
    }
}
