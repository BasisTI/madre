import { Injectable, OnInit } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient, HttpParams } from '@angular/common/http';

export interface ICID {
    id?: number;
    codigo?: string;
    descricao?: string;
    parent?: ICID;
}

export class CID implements ICID {
    constructor(
        public id?: number,
        public codigo?,
        public descricao?: string,
        public parent?: CID,
    ) {}
}
@Injectable({
    providedIn: 'root',
})
export class CidService implements OnInit {
    private readonly baseApi = 'pacientes/api/cids/test';
    private cids: CID[];

    constructor(private client: HttpClient) {}

    ngOnInit(): void {
        this.load();
    }

    buscarPorDescricao(descricao: string) {
        const params = new HttpParams().set('sort', 'descricao').set('descricao', descricao);

        this.client
            .get<CID[]>(this.baseApi, { params })
            .subscribe((cids) => {
                this.setCIDS(cids);
            });
    }

    load(): CidService {
        const params = new HttpParams().set('sort', 'descricao');

        this.client
            .get<CID[]>(this.baseApi, { params })
            .subscribe((cids) => {
                this.setCIDS(cids);
            });

        return this;
    }

    getCIDS(): CID[] {
        return this.cids;
    }

    setCIDS(cids: CID[]): void {
        this.cids = cids;
    }
}
