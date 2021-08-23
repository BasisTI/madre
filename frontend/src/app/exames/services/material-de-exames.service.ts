import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MaterialDeExames } from '../models/subjects/material-de-exames';

@Injectable({
  providedIn: 'root'
})
export class MaterialDeExamesService {
  private readonly apiURL = '/madreexames/api/material-de-exames';

  constructor(private httpService: HttpClient) { }

  cadastrarMaterialDeExame(material: MaterialDeExames) {
    console.log(material);

    return this.httpService.post(this.apiURL, material);
  }

  pegarMaterial(): Observable<Array<MaterialDeExames>> {
    return this.httpService.get<Array<MaterialDeExames>>(this.apiURL);
  }

}
