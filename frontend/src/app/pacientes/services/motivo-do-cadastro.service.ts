import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { MotivoDoCadastro } from '../models/dropdowns/types/motivo-do-cadastro';
import { Observable } from 'rxjs';
@Injectable({
    providedIn: 'root',
})
export class MotivoDoCadastroService extends CrudServiceNuvem<number, MotivoDoCadastro> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/motivo-do-cadastros?sort=valor', httpClient);
    }

    getListaDeMotivos(): Observable<MotivoDoCadastro[]> {
        return this.httpClient.get<MotivoDoCadastro[]>('pacientes/api/motivo-do-cadastros');
    }
}
