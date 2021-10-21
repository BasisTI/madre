import { HttpParams } from '@angular/common/http';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class ListarDarAltaAoPacienteService {
    private readonly URL = '/internacao/api';

    constructor(private httpClient: HttpClient) {}

    public getListaInternacoes(
        id: string,
        dataDaInternacao: string,
        dataDaAlta: string,
        leitosId: string,
        unidadeFuncionalId: string,
        especialidadeId: string,
        convenidoSaudeId: string,
        ativo: string,
        tipoDeAlta: string,
    ): Observable<DarAltaAoPaciente[]> {
        return this.httpClient.get<DarAltaAoPaciente[]>(`${this.URL}/_search/internacoes`, {
            params: new HttpParams()
                .set('id', id)
                .set('dataDaInternacao', dataDaInternacao)
                .set('dataDaAlta', dataDaAlta)
                .set('leitosId', leitosId)
                .set('unidadeFuncionalId', unidadeFuncionalId)
                .set('especialidadeId', especialidadeId)
                .set('convenidoSaudeId', convenidoSaudeId)
                .set('ativo', ativo)
                .set('tipoDeAlta', tipoDeAlta),
        });
    }
}
