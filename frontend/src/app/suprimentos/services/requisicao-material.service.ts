import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class RequisicaoMaterialService {
    private readonly resource = `${api}/requisicoes-materiais`;

    constructor(private client: HttpClient) {}

    public getResource(): string {
        return this.resource;
    }

    public gerarRequisicaoMaterial(requisicao: any): Observable<any> {
        return this.client.post<any>(this.resource, requisicao);
    }

    public efetivarRequisicao(id: number): Observable<any> {
        return this.client.put<any>(`${this.resource}/nao-efetivadas/${id}`, {});
    }
}
