import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RecomendacoesExames } from '../models/subjects/grupos-de-recomendacoes-de-exames';


@Injectable({
  providedIn: 'root'
})
export class GruposDeRecomendacoesDeExamesService {

  private readonly apiUrl = '/madreexames/api/recomendacaos'; 

  constructor(private httpService: HttpClient) { }

  cadastrarGrupos(recomendacoesExames:RecomendacoesExames){
    return this.httpService.post(this.apiUrl, recomendacoesExames)
  }
}
