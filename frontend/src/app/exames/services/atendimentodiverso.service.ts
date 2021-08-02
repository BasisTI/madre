import { AtendimentoDiverso } from './../models/atendimentoDiverso';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class AtendimentoDiversoService {
    private readonly apiUrl = '/madreexames/api/atendimento-diversos';

    constructor(private httpService: HttpClient) {}

    cadastrarAtendimento(atendimentoDiverso: AtendimentoDiverso) {
    
        return this.httpService.post(this.apiUrl, atendimentoDiverso);
    }
}