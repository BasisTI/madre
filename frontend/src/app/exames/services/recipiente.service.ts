import { RecipienteI } from '../models/subjects/recipiente';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})

export class RecipienteService {

    private readonly URL = 'madreexames/api';

    constructor (private httpClient: HttpClient) { }

    cadastrarRecipiente(recipiente: RecipienteI){
        return this.httpClient.post(`${this.URL}/recipientes`, recipiente);
    }
}
