import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CidService, CID } from './../solicitacao-de-internacao/cid.service';
import { Component, Input } from '@angular/core';
import { TreeNode } from 'primeng/api';

@Component({
    selector: 'app-pesquisa-cid',
    templateUrl: './pesquisa-cid.component.html',
    styles: [
        `
            .ui-float-label {
                margin: 5px;
            }
        `,
    ],
})
export class PesquisaCidComponent {
    @Input() group: FormGroup;
    @Input() label = 'CID';
    @Input() required = false;

    constructor(private fb: FormBuilder, public service: CidService) {}
}
