import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Feriado } from '../models/feriado.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RequestUtil } from 'src/app/util/request.util';
import { DataTable } from 'primeng/datatable';

@Injectable({
  providedIn: 'root'
})
export class ServicesService {

  resourceUrl = environment.apiUrl + '/feriados/';
  
  constructor(private httpClient: HttpClient) { }

  getFeriadoFilter(feriado: Feriado, datatable: DataTable): Observable<any>{
    return this.httpClient.post(this.resourceUrl + 'filter', feriado, {params: RequestUtil.getRequestParams(datatable)})
  }

  findByid(id: number): Observable<any> {
    return this.httpClient.get(`${this.resourceUrl}/${id}`);
  }

  save(registro: any): Observable<any> {
    return this.httpClient.post(this.resourceUrl, registro);
  }

  inativar(id: number): Observable<any>{
    return this.httpClient.delete(`${this.resourceUrl}/${id}`);
  }
}
