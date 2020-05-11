import { Prescricao } from './dispensacao/prescricao';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class FarmaciaService {
    constructor(private httpServe: HttpClient) {}
    private readonly apiUrl = '/farmacia/api';

    getPrescricao() {
        return this.httpServe.get<Prescricao[]>(`${this.apiUrl}/prescricao`);
    }
}
