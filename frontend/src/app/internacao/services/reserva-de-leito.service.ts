import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ReservaDeLeitoService {
    private readonly _resource$ = `${api}/reservas`;

    constructor(private client: HttpClient) {}

    reservarLeito(reserva: any): Observable<any> {
        const dto = {
            leitoId: reserva.leito.id,
            tipoDaReservaId: reserva.tipoDaReserva.id,
            origemId: reserva.origem.id,
            dataDoLancamento: reserva.dataDoLancamento,
            dataInicio: reserva.dataInicio,
            dataFim: reserva.dataFim,
            justificativa: reserva.justificativa,
        };

        return this.client.post<any>(this._resource$, dto);
    }
}
