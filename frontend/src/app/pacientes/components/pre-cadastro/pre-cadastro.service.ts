import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PreCadastroModel } from '../../models/pre-cadastro-model';

@Injectable({
    providedIn: 'root',
})
export class PreCadastroService {
    private readonly apiUrl = 'pacientes/api';

    constructor(private httpService: HttpClient) {}

    preCadastrarPaciente(preCadastrando: PreCadastroModel) {
        return this.httpService.post(`${this.apiUrl}/pre-cadastro-pacientes`, preCadastrando);
    }
}
