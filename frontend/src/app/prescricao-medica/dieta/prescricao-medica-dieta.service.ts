import { TipoUnidadeDieta } from './models/tipoUnidadeDieta';
import { PrescricaoDieta } from './models/prescricaoDieta';
import { TipoAprazamento } from './../medicamento/models/tipoAprazamento';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TipoItemDieta } from './models/tipoItemDieta';

@Injectable({
    providedIn: 'root'
})
export class PrescricaoMedicaDietaService {

    private baseUrl = '/prescricao/api';
    sortUrl= '?sort=descricao';

    constructor(private http: HttpClient,
        ) { }

    listarDieta(id: number): Observable<any>{
        return this.http.get(`${this.baseUrl}/prescricao-dietas/paciente/${id}`);

    }

    listarTiposItens(): Observable<Array<TipoItemDieta>> {
        return this.http.get<Array<TipoItemDieta>>(`${this.baseUrl}/tipo-item-dietas${this.sortUrl}`);
    }

    listarTiposAprazamentos(): Observable<Array<TipoAprazamento>> {
        return this.http.get<Array<TipoAprazamento>>(`${this.baseUrl}/tipo-aprazamentos${this.sortUrl}`);
    }

    listarTipoUnidade(): Observable<Array<TipoUnidadeDieta>> {
        return this.http.get<Array<TipoUnidadeDieta>>(`${this.baseUrl}/tipo-unidade-dietas${this.sortUrl}`);
    }

    adicionar(dieta: PrescricaoDieta) {
        return this.http.post<any>(`${this.baseUrl}/prescricao-dietas`, dieta);
    }

}
