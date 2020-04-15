import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, Input } from '@angular/core';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-telefone';

@Component({
    selector: 'app-telefone',
    templateUrl: './telefone.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class TelefoneComponent {
    @Input() telefones: FormGroup;
    opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    telefone: FormGroup = this.fb.group({
        tipo: [''],
        telefone: ['', Validators.required],
        observacao: [''],
        DDD: ['', Validators.required],
    });

    constructor(private fb: FormBuilder) {}

    fakeData = [];
    adicionar() {
        this.fakeData.push(this.telefones.value);
    }
}
