import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { ListaTiposDeQualificacao } from '../models/dropdowns/lista-tipo-de-qualificacao';


@Injectable({
  providedIn: 'root'
})
export class TiposDeQualificaoService{

  private readonly resource = `${api}/tipos-de-qualificacaos`;


  constructor(private client: HttpClient) {}

  getResultCurso(event): Observable<Pageable<ListaTiposDeQualificacao>> {
    return this.client.get<Pageable<ListaTiposDeQualificacao>>(
        `${this.resource}/_resumo`,
        {
            params: new HttpParams().set('descricao', event).set('sort', 'descricao'),
        },
    );
  }

}
