import { Triagem } from './../../models/triagem';
import { Observable } from 'rxjs';
import { ICID } from './../solicitacao-de-internacao/cid.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class TriagemService {
    private readonly apiUrl = 'pacientes/api';

    constructor(private httpService: HttpClient) {}

    cadastrarTriagem(triagem: Triagem) {
        console.log(triagem);
        return this.httpService.post(`${this.apiUrl}/triagens`, triagem);
    }
    alterarTriagem(alterandoTriagem: Observable<any>) {
        return this.httpService.put(`${this.apiUrl}/triagens/:id`, alterandoTriagem);
    }
    listarTriagem(): Observable<any> {
        return this.httpService.get(`${this.apiUrl}/triagens/pacientes/listar`);
    }
}
