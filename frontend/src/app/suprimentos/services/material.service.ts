import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { Material } from '@suprimentos/models/material';
import { Observable } from 'rxjs';

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
