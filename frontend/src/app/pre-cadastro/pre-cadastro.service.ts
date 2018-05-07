import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { PreCadastro } from './pre-cadastro.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class PreCadastroService {

  resourceUrl = environment.apiUrl + '/pre-cadastros';

  searchUrl = environment.apiUrl + '/_search/pre-cadastros';

  constructor(private http: HttpService, private dateUtils: JhiDateUtils) {}

  create(preCadastro: PreCadastro): Observable<PreCadastro> {
    this.converterSusVazio(preCadastro);
    const copy = this.convert(preCadastro);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  private converterSusVazio(preCadastro: PreCadastro) {
    if (preCadastro.numCartaoSus === '') {
      preCadastro.numCartaoSus = undefined;
    }
  }

  update(preCadastro: PreCadastro): Observable<PreCadastro> {
    this.converterSusVazio(preCadastro);
    const copy = this.convert(preCadastro);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<PreCadastro> {
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
   * Convert a returned JSON object to PreCadastro.
   */
  private convertItemFromServer(json: any): PreCadastro {
    const entity: PreCadastro = Object.assign(new PreCadastro(), json);
    entity.dataDeNascimento = this.dateUtils
      .convertLocalDateFromServer(json.dataDeNascimento);
    return entity;
  }

  /**
   * Convert a PreCadastro to a JSON which can be sent to the server.
   */
  private convert(preCadastro: PreCadastro): PreCadastro {
    const copy: PreCadastro = Object.assign({}, preCadastro);
    copy.dataDeNascimento = this.dateUtils
      .convertLocalDateToServer(preCadastro.dataDeNascimento);
    return copy;
  }
}
