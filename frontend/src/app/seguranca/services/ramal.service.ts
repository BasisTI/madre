import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { Ramal } from '../models/dropdowns/ramal';

@Injectable({
  providedIn: 'root'
})
export class RamalService{

  private readonly resource = `${api}/ramals`;


  constructor(private client: HttpClient) {}

  public getRamal(): Observable<Array<Ramal>> {
    return this.client.get<Array<Ramal>>(`${this.resource}`);
  }

}
