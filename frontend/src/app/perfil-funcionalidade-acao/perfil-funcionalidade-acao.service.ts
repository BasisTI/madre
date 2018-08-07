import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Perfil_funcionalidade_acao } from './perfil-funcionalidade-acao.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class Perfil_funcionalidade_acaoService {

  resourceUrl = environment.apiUrl + '/perfil-funcionalidade-acaos';

  searchUrl = environment.apiUrl + '/_search/perfil-funcionalidade-acaos';

  constructor(private http: HttpService) {}

  create(perfil_funcionalidade_acao: Perfil_funcionalidade_acao): Observable<Perfil_funcionalidade_acao> {
    const copy = this.convert(perfil_funcionalidade_acao);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(perfil_funcionalidade_acao: Perfil_funcionalidade_acao): Observable<Perfil_funcionalidade_acao> {
    const copy = this.convert(perfil_funcionalidade_acao);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Perfil_funcionalidade_acao> {
    return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  query(req?: any): Observable<ResponseWrapper> {
    const options = createRequestOption(req);
    return this.http.get(this.resourceUrl, options)
      .map((res: Response) => this.convertResponse(res));
  }

  delete(id: number): Observable<Response> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  private convertResponse(res: Response): ResponseWrapper {
    const jsonResponse = res.json();
    const result = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      result.push(this.convertItemFromServer(jsonResponse[i]));
    }
    return new ResponseWrapper(res.headers, result, res.status);
  }

  /**
   * Convert a returned JSON object to Perfil_funcionalidade_acao.
   */
  private convertItemFromServer(json: any): Perfil_funcionalidade_acao {
    const entity: Perfil_funcionalidade_acao = Object.assign(new Perfil_funcionalidade_acao(), json);
    return entity;
  }

  /**
   * Convert a Perfil_funcionalidade_acao to a JSON which can be sent to the server.
   */
  private convert(perfil_funcionalidade_acao: Perfil_funcionalidade_acao): Perfil_funcionalidade_acao {
    const copy: Perfil_funcionalidade_acao = Object.assign({}, perfil_funcionalidade_acao);
    return copy;
  }
}
