import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class LiberacaoDeLeitoService {
    private readonly _resource$ = `${api}/liberacoes`;

    constructor(private client: HttpClient) {}

    liberarLeito(liberacao: any): Observable<any> {
        console.log(liberacao);

        const dto = {
            leitoId: liberacao.leito.id,
            dataDoLancamento: liberacao.dataDoLancamento,
            justificativa: liberacao.justificativa,
        };

        return this.client.post<any>(this._resource$, dto);
    }
}
