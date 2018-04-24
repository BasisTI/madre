import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { UnidadeHospitalar } from './unidade-hospitalar.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class UnidadeHospitalarService {

  resourceUrl = environment.apiUrl + '/unidade-hospitalars';

  searchUrl = environment.apiUrl + '/_search/unidade-hospitalars';

  constructor(private http: HttpService) {}

  create(unidadeHospitalar: UnidadeHospitalar): Observable<UnidadeHospitalar> {
    const copy = this.convert(unidadeHospitalar);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(unidadeHospitalar: UnidadeHospitalar): Observable<UnidadeHospitalar> {
    const copy = this.convert(unidadeHospitalar);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<UnidadeHospitalar> {
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
   * Convert a returned JSON object to UnidadeHospitalar.
   */
  private convertItemFromServer(json: any): UnidadeHospitalar {
    const entity: UnidadeHospitalar = Object.assign(new UnidadeHospitalar(), json);
    return entity;
  }

  /**
   * Convert a UnidadeHospitalar to a JSON which can be sent to the server.
   */
  private convert(unidadeHospitalar: UnidadeHospitalar): UnidadeHospitalar {
    const copy: UnidadeHospitalar = Object.assign({}, unidadeHospitalar);
    return copy;
  }
}
