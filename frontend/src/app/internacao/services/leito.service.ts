import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from './../api';
import { Observable } from 'rxjs';
import { Leito } from '@internacao/models/leito';

@Injectable({
    providedIn: 'root',
})
export class LeitoService {
    private readonly _resource$ = `${api}/leitos`;

    constructor(private client: HttpClient) {}

    obterLeitosLiberados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/liberados`);
    }

    obterLeitosOcupados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/ocupados`);
    }

    obterLeitosReservados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/reservados`);
    }

    obterLeitosBloqueados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/bloqueados`);
    }

    obterLeitosNaoLiberados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this._resource$}/nao-liberados`);
    }
}
