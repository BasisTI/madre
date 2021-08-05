import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { Vinculo } from '../models/dropdowns/vinculo-model';

@Injectable({
  providedIn: 'root'
})
export class VinculoService{

  private readonly resource = `${api}/vinculos`;


  constructor(private client: HttpClient) {}

  public getVinculo(): Observable<Array<Vinculo>> {
    return this.client.get<Array<Vinculo>>(`${this.resource}`);
  }

}
