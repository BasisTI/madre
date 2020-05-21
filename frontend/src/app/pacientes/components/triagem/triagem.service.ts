import { Pageable } from './../../../shared/pageable';
import { TriagemModel } from './../../models/triagem-model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

type NewType = any;

@Injectable({
    providedIn: 'root',
})
export class TriagemService {
    private readonly apiUrl = 'pacientes/api';

    constructor(private httpService: HttpClient) {}

    cadastrarTriagem(triagem: TriagemModel) {
        console.log(triagem);
        return this.httpService.post(`${this.apiUrl}/triagens`, triagem);
    }

    buscarTriagemId(id: number): Observable<any> {
        return this.httpService.get(`${this.apiUrl}/triagens/${id}`);
    }

    alterarTriagem(id: number): Observable<any> {
        return this.httpService.put(`${this.apiUrl}/triagens`, Response);
    }

    listarTriagem(): Observable<any> {
        return this.httpService.get(`${this.apiUrl}/triagens/listagem`);
    }
}
