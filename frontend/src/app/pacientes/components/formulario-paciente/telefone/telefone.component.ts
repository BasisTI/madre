import { FormBuilder, FormArray, Validators } from '@angular/forms';
import { Component, Input } from '@angular/core';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../../models/dropdowns/opcoes-de-tipo-de-telefone';

@Component({
    selector: 'app-telefone',
    templateUrl: './telefone.component.html',
    styleUrls: ['./telefone.component.scss'],
})
export class TelefoneComponent {
    @Input() telefones: FormArray;
    opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    telefone = this.fb.group({
        ddd: ['', Validators.required],
        tipo: ['', Validators.required],
        numero: ['', Validators.required],
        observacao: [''],
    });

    constructor(private fb: FormBuilder) {}

    adicionarTelefoneALista() {
        this.telefones.push(this.telefone);
    }
}
