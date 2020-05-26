import { OrtesesProteses } from './../../models/orteses-protese';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class OrtesesProteseService {

    private baseUrl = 'prescricao/api';
    constructor(private http: HttpClient) { }

    listarOrtesesproteses(): Observable<Array<OrtesesProteses>> {
        return this.http.get<Array<OrtesesProteses>>(`${this.baseUrl}/ortese-protese`);

    }
}
