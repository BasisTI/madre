import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-arvore',
    templateUrl: './arvore.component.html',
})
export class ArvoreComponent implements OnInit {
    public mostrarArvore = false;

    constructor() {}

    ngOnInit(): void {}

    aoAbrir(): void {
        this.mostrarArvore = true;
    }

    aoSelecionarCid(): void {
        console.log('aoSelecionarCid()');
    }
}
