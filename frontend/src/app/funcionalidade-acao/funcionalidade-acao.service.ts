import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Funcionalidade_acao } from './funcionalidade-acao.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class Funcionalidade_acaoService {

  resourceUrl = environment.apiUrl + '/funcionalidade-acaos';

  searchUrl = environment.apiUrl + '/_search/funcionalidade-acaos';

  constructor(private http: HttpService) {}

  create(funcionalidade_acao: Funcionalidade_acao): Observable<Funcionalidade_acao> {
    const copy = this.convert(funcionalidade_acao);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(funcionalidade_acao: Funcionalidade_acao): Observable<Funcionalidade_acao> {
    const copy = this.convert(funcionalidade_acao);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Funcionalidade_acao> {
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
   * Convert a returned JSON object to Funcionalidade_acao.
   */
  private convertItemFromServer(json: any): Funcionalidade_acao {
    const entity: Funcionalidade_acao = Object.assign(new Funcionalidade_acao(), json);
    return entity;
  }

  /**
   * Convert a Funcionalidade_acao to a JSON which can be sent to the server.
   */
  private convert(funcionalidade_acao: Funcionalidade_acao): Funcionalidade_acao {
    const copy: Funcionalidade_acao = Object.assign({}, funcionalidade_acao);
    return copy;
  }
}
