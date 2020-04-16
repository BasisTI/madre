import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { Component, Input } from '@angular/core';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-telefone';

@Component({
    selector: 'app-telefone',
    templateUrl: './telefone.component.html',
    styleUrls: ['./telefone.component.scss'],
})
export class TelefoneComponent {
    @Input() telefones: FormArray;
    opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    telefone = this.fb.group({
        ddd: [''],
        tipo: [''],
        numero: [''],
        observacao: [''],
    });

    constructor(private fb: FormBuilder) {}

    adicionarTelefoneALista() {
        console.log(this.telefone.value);
    }
}
