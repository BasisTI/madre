import { CEP } from './cep.model';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { of, Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CepService extends CrudServiceNuvem<number, CEP> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/cep?sort=cep', httpClient);
    }

    consultaCEP(cep: string): Observable<CEP[]> {
        let params = new HttpParams();
        params = params.append('cep', cep);
        return this.httpClient.get<CEP[]>('pacientes/api/ceps/filtragem', {
            params: params,
        });
    }

    buscarCEP(cep: string) {
        console.log(cep);

        // Nova variável "cep" somente com dígitos.
        cep = cep.replace(/\D/g, '');

        // Verifica se campo cep possui valor informado.
        if (cep !== '') {
            // Expressão regular para validar o CEP.
            const validacep = /^[0-9]{8}$/;

            // Valida o formato do CEP.
            if (validacep.test(cep)) {
                return this.http.get(`pacientes/api/ceps/buscar/${cep}`);
            }
        }

        return of({});
    }
}
