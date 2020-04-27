import { FormControl } from '@angular/forms';
import { CidService } from './../solicitacao-de-internacao/cid.service';
import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-pesquisa-cid',
    templateUrl: './pesquisa-cid.component.html',
    styles: [],
})
export class PesquisaCidComponent {
    @Input() formControl: FormControl;
    @Input() label = 'CID';
    @Input() field = 'descricao';
    @Input() required = false;

    constructor(public service: CidService) {}
}
