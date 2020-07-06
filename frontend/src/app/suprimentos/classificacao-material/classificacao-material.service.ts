import {Injectable} from '@angular/core';
import {api} from "@suprimentos/api";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ClassificacaoMaterial} from "@suprimentos/classificacao-material/classificacao-material";

@Injectable({
    providedIn: 'root'
})
export class ClassificacaoMaterialService {
    private readonly resource = `${api}/classificacoes-materiais`;

    constructor(private httpClient: HttpClient) {
    }

    public getClassificacoesPorDescricao(descricao: string): Observable<ClassificacaoMaterial[]> {
        return this.httpClient.get<ClassificacaoMaterial[]>(this.resource, {
            params: new HttpParams().set('sort', 'id').set('descricao', descricao)
        });
    }
}
