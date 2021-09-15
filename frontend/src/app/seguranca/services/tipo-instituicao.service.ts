import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { ListaInstituicao } from '../models/dropdowns/lista-instituicao';


@Injectable({
  providedIn: 'root'
})
export class InstituicaoService{

  private readonly resource = `${api}/instituicaos`;


  constructor(private client: HttpClient) {}

  getResultInstituicao(event): Observable<Pageable<ListaInstituicao>> {
    return this.client.get<Pageable<ListaInstituicao>>(
        `${this.resource}/_resumo`,
        {
            params: new HttpParams().set('descricao', event).set('sort', 'descricao'),
        },
    );
  }

}
