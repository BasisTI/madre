import { ArvoreCidService } from './arvore-cid.service';
import { TreeNode } from 'primeng/api';
import { Component, OnInit, Output } from '@angular/core';
import { EventEmitter } from 'protractor';

@Component({
    selector: 'app-arvore-cid',
    templateUrl: './arvore-cid.component.html',
    styles: [],
})
export class ArvoreCidComponent {
    nodes: TreeNode[];
    mostrarArvore = false;
    @Output() aoSelecionar = new EventEmitter();

    constructor(public service: ArvoreCidService) {}

    abrirArvore(evento: MouseEvent): void {
        this.mostrarArvore = true;
    }

    aoAbrir(): void {
        this.nodes = this.service.getNodes();
    }
}
