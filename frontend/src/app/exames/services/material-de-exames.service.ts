import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MaterialDeExames } from '../models/subjects/material-de-exames';
import { Anticoagulante } from '../models/subjects/anticoagulante';
import { Recipiente } from '../models/subjects/recipiente';

@Injectable({
  providedIn: 'root'
})
export class MaterialDeExamesService {
  private readonly apiURL = '/madreexames/api';

  constructor(private httpService: HttpClient) { }

  cadastrarMaterialDeExame(material: MaterialDeExames) {
    console.log(material);

    return this.httpService.post(`${this.apiURL}/material-de-exames`, material);
  }

  pegarMaterial(): Observable<Array<MaterialDeExames>> {
    return this.httpService.get<Array<MaterialDeExames>>(`${this.apiURL}/material-de-exames`);
  }

  pegarAnticoagulantes(): Observable<Array<Anticoagulante>> {
    return this.httpService.get<Array<Anticoagulante>>(`${this.apiURL}/anticoagulantes`);
  }

  pegarRecipientes(): Observable<Array<Recipiente>> {
    return this.httpService.get<Array<Recipiente>>(`${this.apiURL}/recipientes`);
  }


}
