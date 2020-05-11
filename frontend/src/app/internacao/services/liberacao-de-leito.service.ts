import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';
import { Leito } from '@internacao/models/leito';

@Injectable({
    providedIn: 'root',
})
export class LiberacaoDeLeitoService implements EntityService {
    private readonly resource = `${api}/eventos-de-leito/liberacoes`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    liberarLeito(liberacao): Observable<Leito> {
        const liberacaoDTO = {
            leitoId: liberacao.leito.id,
            dataDoLancamento: liberacao.dataDoLancamento,
            justificativa: liberacao.justificativa,
        };

        console.log(liberacaoDTO);

        return this.client.post<any>(this.resource, liberacaoDTO);
    }

    getApi(): string {
        return this.resource;
    }
}
