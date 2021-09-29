import { TipoDeMarcacao } from '../models/subjects/tipo-de-marcacao';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TiposDeMarcacaoService {

    private readonly URL = 'madreexames/api';

  constructor(private httpcClient: HttpClient) { }

  cadastrarTipoMarcacao(marcacao: TipoDeMarcacao){
      return this.httpcClient.post(`${this.URL}/tipo-de-marcacaos`,  marcacao);
  }
}
