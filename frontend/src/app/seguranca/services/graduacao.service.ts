import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { GraduacaoModel } from '../models/graduacao-model';

@Injectable({
    providedIn: 'root',
})
export class GraduacaoService {
    private readonly resource = `${api}/graduacaos`;

    constructor(private client: HttpClient) {}

    alterarServidor(graduacao: GraduacaoModel): Observable<any> {
        return this.client.put(`${this.resource}`, graduacao);
    }

    cadastrarServidor(graduacao) {
        return this.client.post(this.resource, graduacao);
    }
}
