import { HttpParams } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { ListaVinculosServidor } from '../models/dropdowns/lista-vinculo-servidor';
import { Vinculo } from '../models/dropdowns/vinculo-model';

@Injectable({
  providedIn: 'root'
})
export class VinculoService{

  private readonly resource = `${api}/vinculos`;


  constructor(private client: HttpClient) {}

  public getVinculo(): Observable<Array<Vinculo>> {
    return this.client.get<Array<Vinculo>>(`${this.resource}`);
  }

  getResultVinculo(event): Observable<Pageable<ListaVinculosServidor>> {
    return this.client.get<Pageable<ListaVinculosServidor>>(
        `${this.resource}/_resumo`,
        {
            params: new HttpParams().set('descricao', event).set('sort', 'descricao'),
        },
    );
  }

}
