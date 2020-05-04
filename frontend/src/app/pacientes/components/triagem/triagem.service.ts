import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class TriagemService {
    private readonly apiUrl = 'pacientes/api';

    constructor(private httpService: HttpClient) {}

    cadastrarTriagem(cadastroTriagem: any) {
        console.log(cadastroTriagem);
        return this.httpService.post(`${this.apiUrl}/triagens`, cadastroTriagem);
    }
    alterarTriagem(cadastroTriagem: any) {
        // console.log(cadastroTriagem);
        return this.httpService.put(`${this.apiUrl}/triagens`, cadastroTriagem);
    }
}
