import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class LiberacaoDeLeitoService {
    private readonly resource = `${api}`;

    constructor(private client: HttpClient) {}
}
