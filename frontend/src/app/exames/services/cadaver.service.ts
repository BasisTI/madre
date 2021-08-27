import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CadaverModel } from "../models/subjects/cadaver-model";
import {ConvenioDeSaude} from "../models/convenioDeSaude.model";
import {HospitalService} from "@internacao/services/hospital.service";
import {Hospital} from "@internacao/models/hospital";


@Injectable({
  providedIn: 'root'
})
export class CadaverService {

  private readonly grupoURL = '/madreexames/api';
  private readonly internacaoURL = '/internacao/api';

  constructor(
    private client: HttpClient
  ) { }

    // Cadastrar Cadaver
    cadastrarObito(cadaver: CadaverModel) {
        return this.client.post(`${this.grupoURL}/cadavers`, cadaver);
    }
    // Buscar Cadaver
  public getCadaver(): Observable<Array<CadaverModel>> {
    return this.client.get<Array<CadaverModel>>(`${this.grupoURL}/cadavers`);
  }
    public getHospitais(): Observable<Array<Hospital>> {
        return this.client.get<Array<Hospital>>(`${this.internacaoURL}/hospitais`)
    }

    // Buscar Convênio de Saúde
    public getConvenioDeSaude(): Observable<Array<ConvenioDeSaude>> {
        return this.client.get<Array<ConvenioDeSaude>>(`${this.internacaoURL}/convenios-de-saude`)
    }
}
