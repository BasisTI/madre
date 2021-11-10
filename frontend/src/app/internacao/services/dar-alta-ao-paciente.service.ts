import { HttpParams } from '@angular/common/http';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class DarAltaAoPacienteService {
    private readonly URL = '/internacao/api';

    constructor(private httpClient: HttpClient) {}

    public getListaInternacoes(
        id: string,
        dataDaInternacao: string,
        dataDaAlta: string,
        ativo: string,
        leitoId: string,
        especialidadeId: string,
        convenioId: string,
        pacienteId: string,
        prontuario: string,
    ): Observable<DarAltaAoPaciente[]> {
        return this.httpClient.get<DarAltaAoPaciente[]>(`${this.URL}/_search/lista-internacoes`, {
            params: new HttpParams()
                .set('id', id)
                .set('dataDaInternacao', dataDaInternacao)
                .set('dataDaAlta', dataDaAlta)
                .set('ativo', ativo)
                .set('leitoId', leitoId)
                .set('especialidadeId', especialidadeId)
                .set('convenioId', convenioId)
                .set('pacienteId', pacienteId)
                .set('prontuario', prontuario),
        });
    }

    darAlta(darAltaAoPaciente: DarAltaAoPaciente) {
        return this.httpClient.put(`${this.URL}/internacoes`, darAltaAoPaciente);
    }
}
