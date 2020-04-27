import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class TriagemService {
    private readonly apiUrl = '/api/triagens';

    constructor(private httpService: HttpClient) {}

    cadastrarTriagem(cadastroTriagem: Observable<any>) {
        this.httpService.post(this.apiUrl, cadastroTriagem);
        console.log(cadastroTriagem);
    }
}
