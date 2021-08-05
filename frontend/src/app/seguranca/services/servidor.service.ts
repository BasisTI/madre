import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { Servidor } from '../models/servidor-model';


@Injectable({
  providedIn: 'root'
})
export class ServidorService{

  private readonly resource = `${api}/servidors`;


  constructor(private client: HttpClient) {}

  getServidor(): Observable<Array<Servidor>> {
    return this.client.get<Array<Servidor>>(`${this.resource}`);
  }

}
