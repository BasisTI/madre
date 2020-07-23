import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { Observable } from 'rxjs';
import { InclusaoSaldoEstoqueDTO } from '../models/inclusao-saldo-estoque';

@Injectable({
    providedIn: 'root',
})
export class EstoqueGeralService {
    private readonly resource = `${api}/estoques-gerais`;

    constructor(private client: HttpClient) {}

    public incluirSaldoEstoque(inclusaoSaldo: InclusaoSaldoEstoqueDTO): Observable<any> {
        return this.client.post<InclusaoSaldoEstoqueDTO>(`${this.resource}/saldo`, inclusaoSaldo);
    }
}
