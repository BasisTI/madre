import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, Input, OnInit } from '@angular/core';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-telefone';

@Component({
    selector: 'app-telefone',
    templateUrl: './telefone.component.html',
    styleUrls: ['./telefone.component.scss'],
})
export class TelefoneComponent {
    @Input() telefones: FormGroup;
    opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;
    listaDeTelefones = [];
    telefone = {};

    constructor(private fb: FormBuilder) {}
    adicionarTelefoneALista() {
        console.log(this.telefones.value);

        this.listaDeTelefones.push(this.telefones.value);
    }
}
