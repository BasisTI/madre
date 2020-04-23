import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

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
export class EspecialidadeService implements OnInit {
    private readonly baseApi = 'pacientes/api/especialidades';
    private especialidades: Especialidade[];

    constructor(private client: HttpClient) {}

    ngOnInit(): void {
        const params: HttpParams = new HttpParams().set('sort', 'especialidade');

        this.client
            .get<Especialidade[]>(this.baseApi, { params })
            .subscribe((especialidades) => {
                this.setEspecialidades(especialidades);
            });
    }

    setEspecialidades(especialidades: Especialidade[]): void {
        this.especialidades = especialidades;
    }

    getEspecialidades(): Especialidade[] {
        return this.especialidades;
    }

    buscarPorNome(nome: string): void {
        const params: HttpParams = new HttpParams().set('especialidade', nome);

        this.client
            .get<Especialidade[]>(this.baseApi, { params })
            .subscribe((especialidades) => {
                this.setEspecialidades(especialidades);
            });
    }
}
