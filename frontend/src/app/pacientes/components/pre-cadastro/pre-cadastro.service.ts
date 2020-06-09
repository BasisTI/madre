import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ListaPreCadastroModel } from '../../models/lista-pre-cadastro-model';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { PreCadastroModel } from '../../models/pre-cadastro-model';

@Injectable({
    providedIn: 'root',
})
export class PreCadastroService extends CrudServiceNuvem<number, PreCadastroModel> {
    private readonly apiUrl = 'pacientes/api';

    constructor(private httpService: HttpClient) {
        super('pacientes/api/pre-cadastro-pacientes', httpService);
    }

    preCadastrarPaciente(preCadastrando: PreCadastroModel) {
        return this.httpService.post(`${this.apiUrl}/pre-cadastro-pacientes`, preCadastrando);
    }
    buscaListaDePacientes(): Observable<Pageable<PreCadastroModel>> {
        return this.httpService.get<Pageable<PreCadastroModel>>(
            `${this.apiUrl}/pre-cadastro-pacientes`,
        );
    }

    buscarPreCadastroPaciente(nome: string): Observable<Pageable<PreCadastroModel>> {
        const params = new HttpParams().set('nome', nome);

        return this.httpService.get<Pageable<PreCadastroModel>>(
            `${this.apiUrl}/lista-de-pacientes-elastic`,
            {
                params,
            },
        );
    }
}
