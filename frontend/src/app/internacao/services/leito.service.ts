import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Leito } from '@internacao/models/leito';
import { Observable } from 'rxjs';
import { api } from './../api';
import { ListaLeito } from '@internacao/models/leito-model';

@Injectable({
    providedIn: 'root',
})
export class LeitoService {
    private readonly _resource$ = `${api}/leitos`;

    constructor(private client: HttpClient) {}

    obterLeitosLiberados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/situacao/liberados`);
    }

    obterLeitosOcupados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/situacao/ocupados`);
    }

    obterLeitosReservados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/situacao/reservados`);
    }

    obterLeitosBloqueados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/situacao/bloqueados`);
    }

    obterLeitosNaoLiberados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/situacao/nao-liberados`);
    }

    listarLeitos(): Observable<Array<ListaLeito>> {
        return this.client.get<Array<ListaLeito>>(`${this._resource$}`);
    }
}
