import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { Almoxarifado } from '@suprimentos/models/almoxarifado';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AlmoxarifadoService {
    private readonly resource = `${api}/almoxarifados`;

    constructor(private httpClient: HttpClient) {}

    public getAlmoxarifadosPorDescricao(descricao: string): Observable<Almoxarifado[]> {
        return this.httpClient.get<Almoxarifado[]>(this.resource, {
            params: new HttpParams().set('sort', 'id').set('descricao', descricao),
        });
    }
}
