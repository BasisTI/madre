import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { Login } from '../models/dropdowns/login-model';

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  private readonly resource = `${api}/usuarios`;


  constructor(private client: HttpClient) {}

  public getUsuario(): Observable<Array<Login>> {
    return this.client.get<Array<Login>>(`${this.resource}`);
  }

}
