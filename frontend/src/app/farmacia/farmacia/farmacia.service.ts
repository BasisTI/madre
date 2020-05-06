import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class FarmaciaService {
    constructor(private httpServe: HttpClient) {}
    private readonly apiUrl = 'farmacia/api/';

    getPrescricao() {
        this.httpServe.get(`${this.apiUrl}/`);
    }
}
