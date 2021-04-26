import { Pageable } from './../../../shared/pageable';
import { ListaPacientesTriagem } from './lista-pacientes-triagem';
import { TriagemModel } from './../../models/triagem-model';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class TriagemService {
    private readonly apiUrl = 'pacientes/api/triagens';

    constructor(private httpService: HttpClient) {}

    cadastrarTriagem(triagem: TriagemModel) {
        return this.httpService.post(`${this.apiUrl}`, triagem);
    }

    buscarTriagemId(id: number): Observable<any> {
        return this.httpService.get(`${this.apiUrl}/${id}`);
    }

    alterarTriagem(id: number): Observable<any> {
        return this.httpService.put(`${this.apiUrl}`, Response);
    }

    listarTriagem(): Observable<any> {
        return this.httpService.get(`${this.apiUrl}/listagem`);
    }

    deletarTriagem(id: number): Observable<any>{
        return this.httpService.delete(`${this.apiUrl}/${id}`);
    }

    getResultPacientes(event): Observable<Pageable<ListaPacientesTriagem>> {
        return this.httpService.get<Pageable<ListaPacientesTriagem>>(
            `${this.apiUrl}/pacientes/_resumo`,
            {
                params: new HttpParams().set('nome', event).set('sort', 'nome'),
            },
        );
    }
}
