import { CID } from '@internacao/models/cid';
import { ArvoreCidService } from '@internacao/services/arvore-cid.service';
import { Component, OnInit } from '@angular/core';
import { TreeNode } from 'primeng/api';

@Component({
    selector: 'app-arvore',
    templateUrl: './arvore.component.html',
})
export class ArvoreComponent implements OnInit {
    public mostrarArvore = false;
    public pais: Array<TreeNode>;
    public cidSelecionado: CID;

    constructor(private arvoreCidService: ArvoreCidService) {}

    ngOnInit(): void {
        this.arvoreCidService.getPais().subscribe((pais: Array<CID>) => {
            this.pais = this.arvoreCidService.getTreeNodeFrom(pais);
        });
    }

    aoAbrir(): void {
        this.mostrarArvore = true;
    }

    aoSelecionarCid(): void {
        console.log('aoSelecionarCid()');
    }

    carregarFilhos(evento: { originalEvent: MouseEvent; node: TreeNode }): void {
        this.arvoreCidService
            .getFilhosPeloIdDoPai(evento.node.data.id)
            .subscribe((filhos: Array<CID>) => {
                this.pais = this.pais.map((pai: TreeNode) => {
                    if (pai.data.id === evento.node.data.id) {
                        return {
                            ...pai,
                            children: filhos.map((filho: CID) => ({
                                label: filho.descricao,
                                data: filho,
                                selectable: true,
                            })),
                        };
                    }

                    return pai;
                });
            });
    }
}
