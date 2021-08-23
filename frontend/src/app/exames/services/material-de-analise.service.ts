import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MaterialDeAnalise } from '../models/subjects/material-de-analise';

@Injectable({
  providedIn: 'root'
})
export class MaterialDeAnaliseService {
  private readonly apiURL = '/madreexames/api/materials';

  constructor(private httpService: HttpClient) { }

  cadastrarMaterial(material: MaterialDeAnalise) {
    console.log(material);

    return this.httpService.post(this.apiURL, material);
  }

  pegarMaterial(): Observable<Array<MaterialDeAnalise>> {
    return this.httpService.get<Array<MaterialDeAnalise>>(this.apiURL);
  }

}
