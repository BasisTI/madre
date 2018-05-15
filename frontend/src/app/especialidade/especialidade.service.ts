import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Especialidade } from './especialidade.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class EspecialidadeService {

  resourceUrl = environment.apiUrl + '/especialidades';

  searchUrl = environment.apiUrl + '/_search/especialidades';

  constructor(private http: HttpService) {}

  create(especialidade: Especialidade): Observable<Especialidade> {
    const copy = this.convert(especialidade);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(especialidade: Especialidade): Observable<Especialidade> {
    const copy = this.convert(especialidade);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Especialidade> {
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
   * Convert a returned JSON object to Especialidade.
   */
  private convertItemFromServer(json: any): Especialidade {
    const entity: Especialidade = Object.assign(new Especialidade(), json);
    return entity;
  }

  /**
   * Convert a Especialidade to a JSON which can be sent to the server.
   */
  private convert(especialidade: Especialidade): Especialidade {
    const copy: Especialidade = Object.assign({}, especialidade);
    return copy;
  }
}
