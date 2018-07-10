import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Triagem } from './triagem.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class TriagemService {

  resourceUrl = environment.apiUrl + '/triagems';

  searchUrl = environment.apiUrl + '/_search/triagems';

  constructor(private http: HttpService, private dateUtils: JhiDateUtils) {}

  create(triagem: Triagem): Observable<Triagem> {
    const copy = this.convert(triagem);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(triagem: Triagem): Observable<Triagem> {
    const copy = this.convert(triagem);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Triagem> {
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
   * Convert a returned JSON object to Triagem.
   */
  private convertItemFromServer(json: any): Triagem {
    const entity: Triagem = Object.assign(new Triagem(), json);
    entity.dataAtendimento = this.dateUtils
      .convertLocalDateFromServer(json.dataAtendimento);
    entity.horaAtendimento = this.dateUtils
      .convertDateTimeFromServer(json.horaAtendimento);
    return entity;
  }

  /**
   * Convert a Triagem to a JSON which can be sent to the server.
   */
  private convert(triagem: Triagem): Triagem {
    const copy: Triagem = Object.assign({}, triagem);
    copy.dataAtendimento = this.dateUtils
      .convertLocalDateToServer(triagem.dataAtendimento);

    copy.horaAtendimento = this.dateUtils.toDate(triagem.horaAtendimento);
    return copy;
  }
}
