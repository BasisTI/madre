import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Funcionalidade } from './funcionalidade.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class FuncionalidadeService {

  resourceUrl = environment.apiUrl + '/funcionalidades';

  searchUrl = environment.apiUrl + '/_search/funcionalidades';

  constructor(private http: HttpService) {}

  create(funcionalidade: Funcionalidade): Observable<Funcionalidade> {
    const copy = this.convert(funcionalidade);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(funcionalidade: Funcionalidade): Observable<Funcionalidade> {
    const copy = this.convert(funcionalidade);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Funcionalidade> {
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
   * Convert a returned JSON object to Funcionalidade.
   */
  private convertItemFromServer(json: any): Funcionalidade {
    const entity: Funcionalidade = Object.assign(new Funcionalidade(), json);
    return entity;
  }

  /**
   * Convert a Funcionalidade to a JSON which can be sent to the server.
   */
  private convert(funcionalidade: Funcionalidade): Funcionalidade {
    const copy: Funcionalidade = Object.assign({}, funcionalidade);
    return copy;
  }
}
