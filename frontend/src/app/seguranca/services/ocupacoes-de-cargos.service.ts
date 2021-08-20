import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { OcupacaoDeCargo } from '../models/dropdowns/ocupacao-de-cargo';
import { OcupacoesDeCargoModel } from '../models/ocupacoes-de-cargo-model';

@Injectable({
  providedIn: 'root'
})
export class OcupacoesDeCargoService{

  private readonly resource = `${api}/ocupacao-de-cargos`;


  constructor(private client: HttpClient) {}

  alterarOcupacoesDeCargo(ocupacoesDeCargo: OcupacoesDeCargoModel): Observable<any> {
    return this.client.put(`${this.resource}`, ocupacoesDeCargo);
  }

  cadastrarOcupacoesDeCargo(ocupacoesDeCargo) {
    return this.client.post(this.resource, ocupacoesDeCargo);
  }

  getOcupacoesDeCargo():Observable<Array<OcupacaoDeCargo>> {
    return this.client.get<Array<OcupacaoDeCargo>>(`${this.resource}`);
}
  
}
