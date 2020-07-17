import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import {
    ITransferenciaAutomaticaDTO,
    TransferenciaAutomaticaDTO,
} from '@suprimentos/models/transferencia-automatica';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class TransferenciaAutomaticaService {
    private readonly resource = `${api}/transferencias-almoxarifado`;

    constructor(private httpClient: HttpClient) {}

    public getResource(): string {
        return this.resource;
    }

    public criarNovaTransferencia(
        transferencia: ITransferenciaAutomaticaDTO,
    ): Observable<TransferenciaAutomaticaDTO> {
        return this.httpClient.post<TransferenciaAutomaticaDTO>(this.resource, transferencia);
    }

    public efetivarTransferencia(id: number): Observable<TransferenciaAutomaticaDTO> {
        return this.httpClient.put<TransferenciaAutomaticaDTO>(
            `${this.resource}/automaticas/nao-efetivadas/${id}`,
            {},
        );
    }
}
