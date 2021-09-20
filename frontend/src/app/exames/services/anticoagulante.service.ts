import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';
import { Anticoagulante } from '../models/subjects/anticoagulante';

@Injectable({
  providedIn: 'root'
})
export class AnticoagulanteService {

  private readonly apiUrl = '/madreexames/api/anticoagulantes';

  constructor(
    private httpService: HttpClient
  ) { }

  cadastrarAnticoagulante(anticoagulante: Anticoagulante) {
    return this.httpService.post(this.apiUrl, anticoagulante);
  }

}
