import { HttpClient, HttpParams } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Material } from './material';
import { Observable } from 'rxjs';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class MaterialService {
    private readonly resource = `${api}/materiais`;
    constructor(private client: HttpClient) {}

    public obterMateriaisPeloNome(nome: string): Observable<Material[]> {
        return this.client.get<Material[]>(this.resource, {
            params: new HttpParams().set('nome', nome),
        });
    }
}
