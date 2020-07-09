import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { MarcaComercial } from '@suprimentos/models/marca-comercial';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class MarcaComercialService {
    private readonly resource = `${api}/marcas-comerciais`;
    constructor(private client: HttpClient) {}

    public obterMarcaComercialPorDescricao(descricao: string): Observable<MarcaComercial[]> {
        return this.client.get<MarcaComercial[]>(this.resource, {
            params: new HttpParams().set('descricao', descricao),
        });
    }
}
