import { Injectable, OnInit } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient, HttpParams } from '@angular/common/http';

export interface ICID {
    id?: number;
    codigo?: string;
    descricao?: string;
}

export class CID implements ICID {
    constructor(public id?: number, public codigo?, public descricao?: string) {}
}
@Injectable({
    providedIn: 'root',
})
export class CidService implements OnInit {
    private readonly baseApi = 'pacientes/api/cids?sort=descricao';
    private cids: CID[];

    constructor(private client: HttpClient) {}

    ngOnInit(): void {
        this.client.get<CID[]>(this.baseApi).subscribe((cids) => {
            this.setCIDS(cids);
        });
    }

    getCIDS(): CID[] {
        return this.cids;
    }

    setCIDS(cids: CID[]): void {
        this.cids = cids;
    }

    buscarPorDescricao(descricao: string) {
        const params = new HttpParams().set('descricao', descricao);

        this.client
            .get<CID[]>(this.baseApi, { params })
            .subscribe((cids) => {
                this.setCIDS(cids);
            });
    }
}
