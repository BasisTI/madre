import { Medicamento } from './medicamentos/Medicamento';
import { Pageable } from './../../shared/pageable';
import { Observable } from 'rxjs';
import { Unidade } from './dispensacao/unidade';
import { Prescricao } from './dispensacao/prescricao';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class FarmaciaService {
    constructor(private httpServe: HttpClient) {}
    private readonly apiUrl = '/farmacia/api';

    getPrescricao(
        nome: string,
        dataInicio: string,
        local: string,
    ): Observable<Pageable<Prescricao>> {
        return this.httpServe.get<Pageable<Prescricao>>(`${this.apiUrl}/prescricao`, {
            params: new HttpParams()
                .set('nome', nome)
                .set('dataInicio', dataInicio)
                .set('local', local),
        });
    }

    getResult(nome: string): Observable<Pageable<Unidade>> {
        return this.httpServe.get<Pageable<Unidade>>(`${this.apiUrl}/prescricao-local`, {
            params: new HttpParams().set('local', nome),
        });
    }

    getMedicamentos(
        codigo: string,
        descricao: string,
        situacao: string,
    ): Observable<Pageable<Medicamento>> {
        return this.httpServe.get<Pageable<Medicamento>>(`${this.apiUrl}/pesquisa-medicamentos`, {
            params: new HttpParams()
                .set('codigo', codigo)
                .set('descricao', descricao)
                .set('ativo', situacao),
        });
    }
}
