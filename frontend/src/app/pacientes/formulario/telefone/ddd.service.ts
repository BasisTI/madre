import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DDD } from '../../models/dropdowns/types/DDD';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class DDDService{

  private readonly resource = `pacientes/api/list/ddd`;

  constructor(private client: HttpClient) {}

  getResultDDD(event): Observable<Array<DDD>> {
    return this.client.get<Array<DDD>>(
        `${this.resource}`,
        {
            params: new HttpParams().set('valor', event).set('sort', 'valor'),
        },
    );
  }
  
}
