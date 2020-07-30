import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { ClassificacaoMaterial } from '@suprimentos/models/classificacao-material';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ClassificacaoMaterialService {
    private readonly resource = `${api}/classificacoes-materiais`;

    constructor(private httpClient: HttpClient) {}

    public getClassificacoesPorDescricao(descricao: string): Observable<ClassificacaoMaterial[]> {
        return this.httpClient.get<ClassificacaoMaterial[]>(this.resource, {
            params: new HttpParams().set('sort', 'id').set('descricao', descricao),
        });
    }
}
