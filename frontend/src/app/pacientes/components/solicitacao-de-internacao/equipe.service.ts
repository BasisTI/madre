import { Especialidade } from './especialidade.service';
import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

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
export class EquipeService implements OnInit {
    private readonly baseApi = 'pacientes/api/equipes';
    private equipes: Equipe[];

    constructor(private client: HttpClient) {}

    ngOnInit(): void {
        const params = new HttpParams().set('sort', 'nome');

        this.client
            .get<Equipe[]>(this.baseApi, { params })
            .subscribe((equipes) => {
                this.setEquipes(equipes);
            });
    }

    getEquipes(): Equipe[] {
        return this.equipes;
    }

    setEquipes(equipes: Equipe[]): void {
        this.equipes = equipes;
    }

    buscarPorEspecialidadeENomeDaEquipe({ id }: Especialidade, nomeDaEquipe: string): void {
        const params = new HttpParams()
            .set('especialidadeId', String(id))
            .set('nome', nomeDaEquipe);

        this.client
            .get<Equipe[]>(this.baseApi, { params })
            .subscribe((equipes) => {
                this.setEquipes(equipes);
            });
    }
}
