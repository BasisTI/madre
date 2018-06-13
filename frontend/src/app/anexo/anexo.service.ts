import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Anexo } from './anexo.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class AnexoService {

  resourceUrl = environment.apiUrl + '/anexos';

  searchUrl = environment.apiUrl + '/_search/anexos';

  constructor(private http: HttpService, private dateUtils: JhiDateUtils) {}

  create(anexo: Anexo): Observable<Anexo> {
    const copy = this.convert(anexo);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(anexo: Anexo): Observable<Anexo> {
    const copy = this.convert(anexo);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Anexo> {
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
   * Convert a returned JSON object to Anexo.
   */
  private convertItemFromServer(json: any): Anexo {
    const entity: Anexo = Object.assign(new Anexo(), json);
    entity.dataCriacao = this.dateUtils
      .convertLocalDateFromServer(json.dataCriacao);
    return entity;
  }

  /**
   * Convert a Anexo to a JSON which can be sent to the server.
   */
  private convert(anexo: Anexo): Anexo {
    const copy: Anexo = Object.assign({}, anexo);
    copy.dataCriacao = this.dateUtils
      .convertLocalDateToServer(anexo.dataCriacao);
    return copy;
  }
}
