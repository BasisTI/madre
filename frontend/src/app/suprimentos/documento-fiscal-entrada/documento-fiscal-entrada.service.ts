import { HttpClient, HttpParams } from '@angular/common/http';

import { DocumentoFiscalEntrada } from './documento-fiscal-entrada';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class DocumentoFiscalEntradaService {
    private readonly resource = `${api}/documentos-fiscais`;

    constructor(private client: HttpClient) {}

    public obterDocumentoPeloNumero(numeroDocumento: string): Observable<DocumentoFiscalEntrada[]> {
        return this.client.get<DocumentoFiscalEntrada[]>(this.resource, {
            params: new HttpParams().set('numeroDocumento', numeroDocumento),
        });
    }
}
