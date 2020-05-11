import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ReservaDeLeitoService implements EntityService {
    private readonly resource = `${api}/eventos-de-leito/reservas`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    reservarLeito(reserva): Observable<any> {
        console.log(reserva);

        const reservaDTO = {
            leitoId: reserva.leito.id,
            origemId: reserva.origem.id,
            tipoId: reserva.tipoDaReserva.id,
            justificativa: reserva.justificativa,
            dataDoLancamento: reserva.dataDoLancamento,
        };

        return this.client.post<any>(this.resource, reservaDTO);
    }

    getApi(): string {
        return this.resource;
    }
}
