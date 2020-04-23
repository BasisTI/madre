import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface IEspecialidade {
    id?: number;
    sigla?: string;
    especialidade?: string;
}

export class Especialidade implements IEspecialidade {
    constructor(public id?: number, public sigla?, public especialidade?: string) {}
}

@Injectable({
    providedIn: 'root',
})
export class EspecialidadeService extends CrudServiceNuvem<number, Especialidade> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/especialidades?sort=especialidade', httpClient);
    }

    buscarEspecialidadePorNome(nome: string): Observable<Especialidade[]> {
        return this.httpClient.get<Especialidade[]>(
            'pacientes/api/especialidades?sort=especialidade&especialidade=' + nome,
        );
    }
}
