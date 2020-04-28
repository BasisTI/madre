import { TreeNode } from 'primeng/api';
import { CidService, CID } from './../solicitacao-de-internacao/cid.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class ArvoreCidService {
    constructor(private service: CidService) {}

    getLabelForNode(cid: CID) {
        return `${cid.codigo} - ${cid.descricao}`;
    }

    getParents(): TreeNode[] {
        return this.service
            .load()
            .getCIDS()
            .filter((cid) => !cid.parent)
            .map((parent) => {
                const label = this.getLabelForNode(parent);
                const selectable = false;
                const data = parent;
                const collapsedIcon = 'pi pi-folder';
                const expandedIcon = 'pi pi-folder';

                return {
                    label,
                    selectable,
                    data,
                    expandedIcon,
                    collapsedIcon,
                };
            });
    }

    getChildren(): TreeNode[] {
        return this.service
            .load()
            .getCIDS()
            .filter((cid) => cid.parent)
            .map((child) => {
                const label = this.getLabelForNode(child);
                const selectable = true;
                const data = child;

                return {
                    label,
                    selectable,
                    data,
                };
            });
    }

    getNodes(): TreeNode[] {
        return this.getParents().map((parent) => {
            parent.children = this.getChildren().filter(
                (child) => child.data.parent.id === parent.data.id,
            );

            return parent;
        });
    }
}
