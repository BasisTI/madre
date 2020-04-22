import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';

export interface IEquipe {
    id?: number;
    numeroDoConselho?: number;
    nome?: string;
    especialidadeId?: number;
}

export class Equipe implements IEquipe {
    constructor(
        public id?: number,
        public numeroDoConselho?: number,
        public nome?: string,
        public especialidadeId?: number,
    ) {}
}

@Injectable({
    providedIn: 'root',
})
export class EquipeService extends CrudServiceNuvem<number, Equipe> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/equipes?sort=nome', httpClient);
    }

    buscarEquipePorEspecialidadeIdENome(id: number, nome: string): Observable<Equipe[]> {
        return this.httpClient.get<Equipe[]>(
            `pacientes/api/equipes?sort=nome&especialidadeId=${id}&nome=${nome}`,
        );
    }
}
