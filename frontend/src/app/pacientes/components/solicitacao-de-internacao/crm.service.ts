import { Injectable, OnInit } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient, HttpParams } from '@angular/common/http';

export interface ICRM {
    id?: number;
    codigo?: string;
    nome?: string;
}

export class CRM implements ICRM {
    constructor(public id?: number, public codigo?, public nome?: string) {}
}

@Injectable({
    providedIn: 'root',
})
export class CrmService implements OnInit {
    private readonly baseApi = 'pacientes/api/crms?sort=nome';
    private crms: CRM[];

    constructor(private client: HttpClient) {}

    ngOnInit(): void {
        this.client.get<CRM[]>(this.baseApi).subscribe((crms) => {
            this.setCRMS(crms);
        });
    }

    getCRMS(): CRM[] {
        return this.crms;
    }

    setCRMS(crms: CRM[]): void {
        this.crms = crms;
    }

    buscarPorNome(nome: string) {
        const params = new HttpParams().set('nome', nome);

        this.client
            .get<CRM[]>(this.baseApi, { params })
            .subscribe((crms) => {
                this.setCRMS(crms);
            });
    }
}
