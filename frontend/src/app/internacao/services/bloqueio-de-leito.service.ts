import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class BloqueioDeLeitoService implements EntityService {
    private readonly resource = `${api}/eventos-de-leito/bloqueios`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    bloquearLeito(bloqueio): Observable<any> {
        console.log(bloqueio);

        const bloqueioDTO = {
            dataDoLancamento: bloqueio.dataDoLancamento,
            justificativa: bloqueio.justificativa,
            leitoId: bloqueio.leito.id,
            motivoId: bloqueio.motivo.id,
        };

        return this.client.post<any>(this.resource, bloqueioDTO);
    }

    getApi(): string {
        return this.resource;
    }
}
