import { PrescricaoProcedimento } from './models/prescricao-procedimento';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class ProcedimentoEspecialService {
    constructor(private http: HttpClient) {}

    baseUrl = '/prescricao/api';

    prescreverProcedimento(prescricao: PrescricaoProcedimento) {
        return this.http.post(`${this.baseUrl}/prescricao-procedimentos`, prescricao);
    }
}
