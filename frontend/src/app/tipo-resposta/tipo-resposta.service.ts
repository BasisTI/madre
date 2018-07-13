import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { TipoResposta } from './tipo-resposta.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class TipoRespostaService {

  resourceUrl = environment.apiUrl + '/tipo-respostas';

  searchUrl = environment.apiUrl + '/_search/tipo-respostas';

  constructor(private http: HttpService) {}

  create(tipoResposta: TipoResposta): Observable<TipoResposta> {
    const copy = this.convert(tipoResposta);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(tipoResposta: TipoResposta): Observable<TipoResposta> {
    const copy = this.convert(tipoResposta);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<TipoResposta> {
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
   * Convert a returned JSON object to TipoResposta.
   */
  private convertItemFromServer(json: any): TipoResposta {
    const entity: TipoResposta = Object.assign(new TipoResposta(), json);
    return entity;
  }

  /**
   * Convert a TipoResposta to a JSON which can be sent to the server.
   */
  private convert(tipoResposta: TipoResposta): TipoResposta {
    const copy: TipoResposta = Object.assign({}, tipoResposta);
    return copy;
  }
}
