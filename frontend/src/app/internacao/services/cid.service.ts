import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CID } from '@internacao/models/cid';

@Injectable({
    providedIn: 'root',
})
export class CidService {
    private readonly _resource$ = `${api}/cids`;

    constructor(private client: HttpClient) {}

    public getCids(): Observable<Array<CID>> {
        return this.client.get<Array<CID>>(this._resource$, {
            params: new HttpParams().set('sort', 'codigo'),
        });
    }

    public getSelectItemArrayFrom(cids: Array<CID>) {
        return cids.map((cid: CID) => ({
            label: `${cid.codigo} - ${cid.descricao}`,
            value: cid,
        }));
    }
}
