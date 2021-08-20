import { Observable } from 'rxjs';
import { Sinonimos } from '../models/subjects/sinonimos';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class SinonimosExamesService {
    private readonly apiUrl = '/madreexames/api/sinonimos';

    constructor(private httpService: HttpClient) {}

    cadastrarSinonimos(sinonimos: Sinonimos) {
    
        return this.httpService.post(this.apiUrl, sinonimos);
    }

    GetSinonimos(): Observable<Array<Sinonimos>> {
        return this.httpService.get<Array<Sinonimos>>(`${this.apiUrl}`);
     }
}