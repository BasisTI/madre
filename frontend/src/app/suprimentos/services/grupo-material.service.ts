import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { GrupoMaterial } from '@suprimentos/models/grupo-material';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class GrupoMaterialService {
    private readonly resource = `${api}/grupos-materiais`;

    constructor(private client: HttpClient) {}

    public getGruposPorDescricao(descricao: string): Observable<GrupoMaterial[]> {
        return this.client.get<GrupoMaterial[]>(this.resource, {
            params: new HttpParams().set('sort', 'descricao').set('descricao', descricao),
        });
    }
}
