import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { ListaPessoasServidor } from '../models/dropdowns/lista-pessoa-servidor';
import { Pessoa } from '../models/pessoa-resumo-model';

@Injectable({
    providedIn: 'root',
})
export class PessoaService {
    private readonly resource = `${api}/pessoas`;

    constructor(private client: HttpClient) {}

    public getPessoa(): Observable<Array<Pessoa>> {
        return this.client.get<Array<Pessoa>>(`${this.resource}`);
    }

    getResultPessoas(event): Observable<Pageable<ListaPessoasServidor>> {
        return this.client.get<Pageable<ListaPessoasServidor>>(`${this.resource}/nao-cadastradas`, {
            params: new HttpParams().set('nome', event).set('sort', 'nome'),
        });
    }
}
