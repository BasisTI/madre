import { UnidadeFuncional } from './../models/unidade-funcional';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TipoDeUnidadeFuncionalService {

    private readonly URL = 'internacao/api';

  constructor(private httpClient: HttpClient) { }

  cadastrarTipoUnidadeFuncional(tipoUnidadeFuncional: UnidadeFuncional){
      return this.httpClient.post(`${this.URL}/tipo-unidades`, tipoUnidadeFuncional);
  }
}
