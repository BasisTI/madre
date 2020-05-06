import { CID } from '@internacao/models/cid';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';
import { TreeNode } from 'primeng/api';

@Injectable({
    providedIn: 'root',
})
export class ArvoreCidService implements EntityService {
    private readonly resource = `${api}/cids`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getPais(): Observable<Array<CID>> {
        return this.client.get<Array<CID>>(`${this.resource}/pais`);
    }

    getFilhosPeloIdDoPai(id: number): Observable<Array<CID>> {
        return this.client.get<Array<CID>>(`${this.resource}/pais/${id}/filhos`);
    }

    getTreeNodeFrom(pais: Array<CID>): Array<TreeNode> {
        return pais.map((pai) => ({
            label: pai.descricao,
            data: pai,
            expandedIcon: 'pi pi-folder-open',
            collapsedIcon: 'pi pi-folder',
            leaf: false,
            selectable: false,
            children: [],
        }));
    }
}
