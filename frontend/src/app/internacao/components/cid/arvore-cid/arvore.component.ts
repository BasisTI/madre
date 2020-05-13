import { Component, EventEmitter, Output } from '@angular/core';
import { CID } from '@internacao/models/cid';
import { ArvoreCidService } from '@internacao/services/arvore-cid.service';
import { TreeNode, Tree } from 'primeng';

@Component({
    selector: 'app-arvore',
    templateUrl: './arvore.component.html',
})
export class ArvoreComponent {
    visible = false;
    selection: CID;
    parents: Array<TreeNode>;
    @Output() onSelect = new EventEmitter<CID>();

    constructor(private arvoreCidService: ArvoreCidService) {}

    onClick() {
        this.visible = true;

        this.arvoreCidService.getParents().subscribe((parents: Array<CID>) => {
            this.parents = this.arvoreCidService.getParentTreeNodesFrom(parents);
        });
    }

    onNodeSelect(event: { originalEvent: MouseEvent; node: TreeNode }) {
        this.onSelect.emit(event.node.data);
    }

    onNodeExpand(event: { originalEvent: MouseEvent; node: TreeNode }) {
        const eventParentId = event.node.data.id;

        this.arvoreCidService
            .getChildrenFromParentId(eventParentId)
            .subscribe((children: Array<CID>) => {
                this.parents = this.parents.map((parent: TreeNode) => {
                    const parentId = parent.data.id;

                    if (eventParentId === parentId) {
                        const node = {
                            ...parent,
                            children: this.arvoreCidService.getChildTreeNodesFrom(children),
                        };

                        return node;
                    }

                    return parent;
                });
            });
    }
}
