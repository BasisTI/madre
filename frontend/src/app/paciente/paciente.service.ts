import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

import { Paciente } from './paciente.model';
import { ResponseWrapper, createRequestOption, JhiDateUtils } from '../shared';

@Injectable()
export class PacienteService {

  resourceUrl = '/pacientes/api/pacientes';
  
  searchUrl = '/pacientes/api/_search/pacientes';

  constructor(private http: HttpService, private dateUtils: JhiDateUtils) {}

  create(paciente: Paciente): Observable<Paciente> {
    const copy = this.convert(paciente);
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  update(paciente: Paciente): Observable<Paciente> {
    const copy = this.convert(paciente);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
      const jsonResponse = res.json();
      return this.convertItemFromServer(jsonResponse);
    });
  }

  find(id: number): Observable<Paciente> {
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
   * Convert a returned JSON object to Paciente.
   */
  private convertItemFromServer(json: any): Paciente {
    const entity: Paciente = Object.assign(new Paciente(), json);
    entity.dataNascimento = this.dateUtils
      .convertLocalDateFromServer(json.dataNascimento);
    return entity;
  }

  /**
   * Convert a Paciente to a JSON which can be sent to the server.
   */
  private convert(paciente: Paciente): Paciente {
    const copy: Paciente = Object.assign({}, paciente);
    copy.dataNascimento = this.dateUtils
      .convertLocalDateToServer(paciente.dataNascimento);
    return copy;
  }
}
