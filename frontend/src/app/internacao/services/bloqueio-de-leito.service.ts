import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class BloqueioDeLeitoService {
    private readonly _resource$ = `${api}/bloqueios`;

    constructor(private client: HttpClient) {}

    bloquearLeito(bloqueio: any): Observable<any> {
        const dto = {
            leitoId: bloqueio.leito.id,
            motivoId: bloqueio.motivo.id,
            dataDoLancamento: bloqueio.dataDoLancamento,
            dataInicio: bloqueio.dataInicio,
            dataFim: bloqueio.dataFim,
            justificativa: bloqueio.justificativa,
        };

        console.log(dto);

        return this.client.post<any>(this._resource$, dto);
    }
}
