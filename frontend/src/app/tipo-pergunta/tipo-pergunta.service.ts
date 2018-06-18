import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { TipoPergunta } from './tipo-pergunta.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class TipoPerguntaService {

  resourceUrl = environment.apiUrl + '/tipo-perguntas';

  searchUrl = environment.apiUrl + '/_search/tipo-perguntas';

  constructor(private http: HttpService) {}

  create(tipoPergunta: TipoPergunta): Observable<TipoPergunta> {
    const copy = this.convert(tipoPergunta);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(tipoPergunta: TipoPergunta): Observable<TipoPergunta> {
    const copy = this.convert(tipoPergunta);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<TipoPergunta> {
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
   * Convert a returned JSON object to TipoPergunta.
   */
  private convertItemFromServer(json: any): TipoPergunta {
    const entity: TipoPergunta = Object.assign(new TipoPergunta(), json);
    return entity;
  }

  /**
   * Convert a TipoPergunta to a JSON which can be sent to the server.
   */
  private convert(tipoPergunta: TipoPergunta): TipoPergunta {
    const copy: TipoPergunta = Object.assign({}, tipoPergunta);
    return copy;
  }
}
