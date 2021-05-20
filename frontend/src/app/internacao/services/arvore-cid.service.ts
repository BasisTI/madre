import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { api } from '@internacao/api';
import { CID } from '@internacao/models/cid';
import { TreeNode } from 'primeng/api';

@Injectable({
    providedIn: 'root',
})
export class ArvoreCidService {
    private readonly _resource$ = `${api}/cids`;

    constructor(private client: HttpClient) {}

    public getParents(): Observable<Array<CID>> {
        return this.client.get<Array<CID>>(`${this._resource$}/pais`, {
            params: new HttpParams().set('sort', 'codigo'),
        });
    }

    public getParentTreeNodesFrom(parents: Array<CID>): Array<TreeNode> {
        return parents.map((parent: CID) => ({
            label: `${parent.codigo} - ${parent.descricao}`,
            data: parent,
            expandedIcon: 'pi pi-folder-open',
            collapsedIcon: 'pi pi-folder',
            leaf: false,
        }));
    }

    public getChildrenFromParentId(id: number): Observable<Array<CID>> {
        return this.client.get<Array<CID>>(`${this._resource$}/pais/${id}/filhos`, {
            params: new HttpParams().set('sort', 'codigo'),
        });
    }

    public getChildTreeNodesFrom(children: Array<CID>): Array<TreeNode> {
        return children.map((child: CID) => {
            return {
                label: `${child.codigo} - ${child.descricao}`,
                data: child,
                expandedIcon: 'pi pi-folder-open',
                collapsedIcon: 'pi pi-folder',
            };
        });
    }
}
