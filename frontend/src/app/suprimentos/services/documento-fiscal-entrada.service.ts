import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { DocumentoFiscalEntrada } from '@suprimentos/models/documento-fiscal-entrada';
import { Observable } from 'rxjs';

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

    public criarNotaFiscal(documento: DocumentoFiscalEntrada): Observable<DocumentoFiscalEntrada> {
        return this.client.post<DocumentoFiscalEntrada>(this.resource, documento);
    }
}
