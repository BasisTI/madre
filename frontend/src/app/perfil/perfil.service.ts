import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Perfil } from './perfil.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class PerfilService {

  resourceUrl = environment.apiUrl + '/perfils';

  searchUrl = environment.apiUrl + '/_search/perfils';

  constructor(private http: HttpService) {}

  create(perfil: Perfil): Observable<Perfil> {
    const copy = this.convert(perfil);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(perfil: Perfil): Observable<Perfil> {
    console.log("ID perfil: "+ perfil.id);
    const copy = this.convert(perfil);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Perfil> {
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
   * Convert a returned JSON object to Perfil.
   */
  private convertItemFromServer(json: any): Perfil {
    const entity: Perfil = Object.assign(new Perfil(), json);
    return entity;
  }

  /**
   * Convert a Perfil to a JSON which can be sent to the server.
   */
  private convert(perfil: Perfil): Perfil {
    const copy: Perfil = Object.assign({}, perfil);
    return copy;
  }
}
