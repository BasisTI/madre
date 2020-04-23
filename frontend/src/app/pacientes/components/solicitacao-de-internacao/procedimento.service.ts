import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
export interface IProcedimento {
    id?: number;
    codigo?: string;
    procedimento?: string;
}

export class Procedimento implements IProcedimento {
    constructor(public id?: number, public codigo?, public procedimento?: string) {}
}

@Injectable({
    providedIn: 'root',
})
export class ProcedimentoService implements OnInit {
    private readonly baseApi = 'pacientes/api/procedimentos?sort=procedimento';
    private procedimentos: Procedimento[];

    constructor(private client: HttpClient) {}

    ngOnInit(): void {
        this.client.get<Procedimento[]>(this.baseApi).subscribe((procedimentos) => {
            this.setProcedimentos(procedimentos);
        });
    }

    getProcedimentos(): Procedimento[] {
        return this.procedimentos;
    }

    setProcedimentos(procedimentos: Procedimento[]): void {
        this.procedimentos = procedimentos;
    }

    buscarPorNome(nome: string) {
        const params = new HttpParams().set('procedimento', nome);

        this.client
            .get<Procedimento[]>(this.baseApi, { params })
            .subscribe((procedimentos) => {
                this.setProcedimentos(procedimentos);
            });
    }
}
