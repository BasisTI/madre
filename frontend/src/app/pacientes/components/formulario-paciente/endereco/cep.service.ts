import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CepService {
    constructor(private http: HttpClient) {}

    consultaCEP(cep: string) {
        console.log(cep);

        cep = cep.replace(/\D/g, '');

        if (cep != '') {
            const validaCep = /^[0-9]{8}$/;

            if (validaCep.test(cep)) {
                return this.http.get(`//viacep.com.br/ws${cep}/json`);
            }
        }
        return of({});
    }
}
