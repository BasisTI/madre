import { ArvoreCidService } from './arvore-cid.service';
import { TreeNode } from 'primeng/api';
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'app-arvore-cid',
    templateUrl: './arvore-cid.component.html',
    styles: [],
})
export class ArvoreCidComponent {
    nodes: TreeNode[];
    mostrarArvore = false;
    @Output() aoSelecionar = new EventEmitter();
    selecionado;

    constructor(public service: ArvoreCidService) {}

    carregar(evento) {
        console.log(evento);
    }

    selecionar(evento: { originalEvent: MouseEvent; node: TreeNode }): void {
        this.aoSelecionar.emit(evento);
    }

    abrirArvore(evento: MouseEvent): void {
        this.mostrarArvore = true;
    }

    aoAbrir(): void {
        this.nodes = this.service.getNodes();
    }
}
