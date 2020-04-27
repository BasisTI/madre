import { Observable } from 'rxjs';
import { FormularioCadastroComponent } from './formulario-cadastro.component';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class FormulaCadastroService {
    private readonly apiUrl = '/pacientes/api/pacientes';

    constructor(private httpService: HttpClient) {}
    //oi
    cadastrarPaciente(cadastro: any) {
        console.log(cadastro);

        return this.httpService.post(this.apiUrl, cadastro);
    }
}
