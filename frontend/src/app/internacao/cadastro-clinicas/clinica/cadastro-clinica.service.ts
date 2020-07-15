import { Clinica } from '@internacao/formulario-unidades/models/dropwdowns/Clinica';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CadastroClinica {
    private readonly apiUrl = '/internacao/api/clinicas';

    constructor(private httpService: HttpClient) {}

    cadastrarClinica(clinica: Clinica) {
        console.log(clinica);

        return this.httpService.post(this.apiUrl, clinica);
    }
}
