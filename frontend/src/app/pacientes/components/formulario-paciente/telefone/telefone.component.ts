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
        ddd: [null, Validators.required],
        tipo: [null, Validators.required],
        numero: [null, Validators.required],
        observacao: [null],
    });

    constructor(private fb: FormBuilder) {}

    adicionarTelefoneALista() {
        if (this.telefone.valid) {
            this.telefones.push(this.telefone);
            this.telefone = this.fb.group({
                ddd: [null, Validators.required],
                tipo: [null, Validators.required],
                numero: [null, Validators.required],
                observacao: [null],
            });
        }
        this.telefone.reset();
    }
}
