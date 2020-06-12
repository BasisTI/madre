import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CID } from '@internacao/models/cid';
import { SelectItem } from 'primeng';

@Injectable({
    providedIn: 'root',
})
export class CidService {
    private readonly resource = `${api}/cids`;

    constructor(private client: HttpClient) {
    }

    public getCids(): Observable<CID[]> {
        return this.client.get<Array<CID>>(this.resource, {
            params: new HttpParams().set('sort', 'codigo'),
        });
    }

    public getPais(): Observable<CID[]> {
        return this.client.get<CID[]>(`${this.resource}/pais`, {
            params: new HttpParams().set('sort', 'codigo'),
        });
    }

    public getFilhosPeloIdDoPai(id: number): Observable<CID[]> {
        return this.client.get<CID[]>(`${this.resource}/pais/${id}/filhos`, {
            params: new HttpParams().set('sort', 'codigo'),
        });
    }

    public getSelectItemArrayFrom(cids: CID[]): SelectItem[] | null {
        if (!cids) {
            return null;
        }

        return cids.map((cid: CID) => ({
            label: `${cid.codigo} - ${cid.descricao}`,
            value: cid,
        }));
    }
}
