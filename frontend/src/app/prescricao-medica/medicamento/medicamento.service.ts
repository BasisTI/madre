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

    baseUrl = '/prescricao/api';

    constructor(private http: HttpClient
    ) { }

    listarMedicamentos(): Observable<any> {
        return this.http.get(`${this.baseUrl}/medicamentos`);
    }

    listarListaMedicamentos(): Observable<any> {
        return this.http.get(`${this.baseUrl}/lista-medicamentos`);
    }

    listarUnidadeDose(): Observable<Array<UnidadeDose>>{
        return this.http.get<Array<UnidadeDose>>(`${this.baseUrl}/unidade-doses`);
    }

    listarViaAdministracao(): Observable<Array<ViasAdministracao>>{
        return this.http.get<Array<ViasAdministracao>>(`${this.baseUrl}/vias-administracaos`);
    }

    listarTiposAprazamentos(): Observable<Array<TipoAprazamento>> {
        return this.http.get<Array<TipoAprazamento>>(`${this.baseUrl}/tipo-aprazamento`);
    }
}