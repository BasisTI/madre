import { TipoMedicamento } from './cadastro-medicamento/tipoMedicamento';
import { Medicamento, Medicamentos } from './medicamentos/Medicamento';
import { Pageable } from './../../shared/pageable';
import { Observable } from 'rxjs';
import { Unidade } from './dispensacao/unidade';
import { Prescricao, Prescricaos } from './dispensacao/prescricao';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apresentacao } from './cadastro-medicamento/apresentacao';

@Injectable({
    providedIn: 'root',
})
export class FarmaciaService {
    medicamento: Medicamentos = new Medicamentos();
    prescricao: Prescricaos = new Prescricaos();
    constructor(private httpServe: HttpClient) {}
    private readonly apiUrl = '/farmacia/api';
    private readonly api = '/farmacia/api/medicamentos';

    getPrescricao(
        nome: string,
        dataInicio: string,
        local: string,
    ): Observable<Pageable<Prescricao>> {
        return this.httpServe.get<Pageable<Prescricao>>(`${this.apiUrl}/prescricao`, {
            params: new HttpParams()
                .set('nome', nome)
                .set('dataInicio', dataInicio)
                .set('local', local),
        });
    }

    getResultTipoMedicamento(event?): Observable<Array<TipoMedicamento>> {
        return this.httpServe.get<Array<TipoMedicamento>>(`${this.apiUrl}/tipo-medicamentos`, {
            params: new HttpParams().set('nome', event),
        });
    }
    getResultUnidade(event?): Observable<Array<Unidade>> {
        return this.httpServe.get<Array<Unidade>>(`${this.apiUrl}/unidades`, {
            params: new HttpParams().set('nome', event),
        });
    }
    getResultApresentacao(event?): Observable<Array<Apresentacao>> {
        return this.httpServe.get<Array<Apresentacao>>(`${this.apiUrl}/apresentacaos`, {
            params: new HttpParams().set('nome', event),
        });
    }

    cadastrar(medicamento: any): Observable<any> {
        const dto = {
            codigo: null,
            nome: medicamento.nome,
            descricao: medicamento.descricao,
            concentracao: medicamento.concentracao,
            ativo: medicamento.ativo,

            apresentacaoId: {
                id: medicamento.apresentacao.id,
                nome: medicamento.apresentacao.nome,
            },

            unidadeId: {
                id: medicamento.unidade.id,
                nome: medicamento.unidade.nome,
            },

            tipoMedicamentoId: null,
        };

        return this.httpServe.post<any>(`${this.apiUrl}/medicamentos`, dto);
    }

    editar(medicamento: any): Observable<any> {
        const dto = {
            id: medicamento.id,
            nome: medicamento.nome,
            descricao: medicamento.descricao,
            concentracao: medicamento.concentracao,
            ativo: medicamento.ativo,

            apresentacaoId: {
                id: medicamento.apresentacao.id,
                nome: medicamento.apresentacao.nome,
            },

            unidadeId: {
                id: medicamento.unidade.id,
                nome: medicamento.unidade.nome,
            },

            tipoMedicamentoId: null,
        };

        return this.httpServe.put<any>(`${this.api}`, dto);
    }

    getMedicamentos(
        nome: string,
        descricao: string,
        situacao: string,
    ): Observable<Pageable<Medicamento>> {
        return this.httpServe.get<Pageable<Medicamento>>(`${this.apiUrl}/_search/medicamentos`, {
            params: new HttpParams()
                .set('nome', nome)
                .set('descricao', descricao)
                .set('ativo', situacao),
        });
    }

    exportarMedicamentos(): Observable<Blob> {
        return this.httpServe.get(`${this.apiUrl}/medicamentos/exportar`, { responseType: 'blob' });
    }

    find(id: number): Observable<Medicamentos> {
        return this.httpServe.get(`${this.apiUrl}/medicamentos/${id}`);
    }

    delete(id: number): Observable<Medicamentos> {
        return this.httpServe.delete(`${this.apiUrl}/medicamentos/${id}`);
    }

    findP(id: number): Observable<Prescricaos> {
        return this.httpServe.get(`${this.apiUrl}/prescricao/${id}`);
    }

    deleteP(id: number): Observable<Prescricaos> {
        return this.httpServe.delete(`${this.apiUrl}/prescricao/${id}`);
    }
}
