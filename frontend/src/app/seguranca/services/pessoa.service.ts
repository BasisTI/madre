import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
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

}
