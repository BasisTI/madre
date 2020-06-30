import { Unidade } from './../models/unidade';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CadastroService {
    private readonly apiUrl = '/internacao/api/unidades';

    constructor(private httpService: HttpClient) {}

    cadastrarUnidade(unidade: Unidade) {
        console.log(unidade);

        return this.httpService.post(this.apiUrl, unidade);
    }
}
