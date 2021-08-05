import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { ListaPessoasServidor } from '../models/lista-pessoa-servidor';
import { Pessoa } from '../models/pessoa-model';

@Injectable({
  providedIn: 'root'
})
export class PessoaService{

  private readonly resource = `${api}/pessoas`;


  constructor(private client: HttpClient) {}

  public getPessoa(): Observable<Array<Pessoa>> {
    return this.client.get<Array<Pessoa>>(`${this.resource}`);
  }

  getResultPessoas(event): Observable<Pageable<ListaPessoasServidor>> {
    return this.client.get<Pageable<ListaPessoasServidor>>(
        `${this.resource}/_resumo`,
        {
            params: new HttpParams().set('nome', event).set('sort', 'nome'),
        },
    );
  }

}
