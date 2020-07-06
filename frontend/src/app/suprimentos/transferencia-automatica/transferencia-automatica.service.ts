import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {api} from '@suprimentos/api';
import {Observable} from 'rxjs';
import {
    ITransferenciaAutomaticaDTO,
    TransferenciaAutomaticaDTO
} from '@suprimentos/transferencia-automatica/transferencia-automatica';

@Injectable({
    providedIn: 'root'
})
export class TransferenciaAutomaticaService {
    private readonly resource = `${api}/transferencias-almoxarifado`;

    constructor(private httpClient: HttpClient) {
    }

    public getResource(): string {
        return this.resource;
    }

    public criarNovaTransferencia(transferencia: ITransferenciaAutomaticaDTO): Observable<TransferenciaAutomaticaDTO> {
        return this.httpClient.post<TransferenciaAutomaticaDTO>(this.resource, transferencia);
    }
}
