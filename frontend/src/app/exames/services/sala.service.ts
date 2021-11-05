import { Sala } from '../models/subjects/sala';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SalasService {
    private readonly apiUrl = '/madreexames/api/salas';

    constructor(private httpService: HttpClient) {}

    public getSalasPorUnidade(unidadeExecutoraId: string, ativo: string): Observable<Array<Sala>> {
        return this.httpService.get<Array<Sala>>(`/madreexames/api/_search/listar-salas`, {
            params: new HttpParams()
                .set('unidadeExecutoraId', unidadeExecutoraId)
                .set('ativo', ativo),
        });
    }

    public getSalasFiltradas(
        id: string,
        nome: string,
        locacao: string,
        ativo: string,
        unidadeExecutoraId: string,
    ): Observable<Array<Sala>> {
        return this.httpService.get<Array<Sala>>(`/madreexames/api/_search/listar-salas`, {
            params: new HttpParams()
                .set('id', id)
                .set('nome', nome)
                .set('locacao', locacao)
                .set('ativo', ativo)
                .set('unidadeExecutoraId', unidadeExecutoraId),
        });
    }


    cadastrarSala(sala: Sala) {
        return this.httpService.post(this.apiUrl, sala);
    }
}
