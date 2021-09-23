import { Sala } from './../models/subjects/sala';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class SalasService {
  
  private readonly apiUrl = '/madreexames/api/salas';

  constructor(
    private httpService: HttpClient
  ) { }

  cadastrarSala(sala: Sala) {
    return this.httpService.post(this.apiUrl, sala);
  }

}
