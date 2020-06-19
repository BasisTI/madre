import { Medicamento } from './../../farmacia/farmacia/medicamentos/Medicamento';
import { PrescricaoMedicamento } from './models/prescricaoMedicamento';
import { UnidadeInfusao } from './models/unidadeInfusao';
import { Diluente } from './models/diluente';
import { TipoAprazamento } from './models/tipoAprazamento';
import { ViasAdministracao } from './models/viasAdministracao';
import { UnidadeDose } from './models/unidadeDose';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})

export class MedicamentoService {

    urlFarmacia = '/farmacia/api'
    baseUrl = '/prescricao/api';
    sortUrl= '?sort=descricao';

    constructor(private http: HttpClient
    ) { }

    listarMedicamentos(nome: string): Observable<any> {
        return this.http.get(`${this.urlFarmacia}/_search/medicamentos?nome=${nome}`);
    }

    listarListaMedicamentos(): Observable<any> {
        return this.http.get(`${this.baseUrl}/lista-medicamentos`);
    }

    listarUnidadeDose(): Observable<Array<UnidadeDose>>{
        return this.http.get<Array<UnidadeDose>>(`${this.baseUrl}/unidade-doses${this.sortUrl}`);
    }

    listarViaAdministracao(): Observable<Array<ViasAdministracao>>{
        return this.http.get<Array<ViasAdministracao>>(`${this.baseUrl}/vias-administracaos${this.sortUrl}`);
    }

    listarTiposAprazamentos(): Observable<Array<TipoAprazamento>> {
        return this.http.get<Array<TipoAprazamento>>(`${this.baseUrl}/tipo-aprazamentos${this.sortUrl}`);
    }

    listarDiluentes(): Observable<Array<Diluente>> {
        return this.http.get<Array<Diluente>>(`${this.baseUrl}/diluentes${this.sortUrl}`);
    }

    listarUnidadeInfusao(): Observable<Array<UnidadeInfusao>> {
        return this.http.get<Array<UnidadeInfusao>>(`${this.baseUrl}/unidade-infusaos${this.sortUrl}`);
    }

    prescreverMedicamento(prescricao: PrescricaoMedicamento){
        return this.http.post(`${this.baseUrl}/prescricao-medicamentos`, prescricao);
    }


}
