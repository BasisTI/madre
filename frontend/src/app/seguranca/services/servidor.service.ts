import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { ListaServidor } from '../models/dropdowns/lista-servidor';
import { Servidor } from '../models/dropdowns/servidor-model';
import { ServidorModel } from '../models/servidor-model';



@Injectable({
  providedIn: 'root'
})
export class ServidorService{

  private readonly resource = `${api}/servidors`;


  constructor(private client: HttpClient) {}

  getServidor(): Observable<Array<Servidor>> {
    return this.client.get<Array<Servidor>>(`${this.resource}`);
  }

  alterarServidor(servidor: ServidorModel): Observable<any> {
    return this.client.put(`${this.resource}`, servidor);
  }

  cadastrarServidor(servidor) {
    return this.client.post(this.resource, servidor);
  }

  getResultServidor(event): Observable<Pageable<ListaServidor>> {
    return this.client.get<Pageable<ListaServidor>>(
        `${this.resource}/_resumo`,
        {
            params: new HttpParams().set('matricula', event).set('sort', 'matricula'),
        },
    );
  }

}
