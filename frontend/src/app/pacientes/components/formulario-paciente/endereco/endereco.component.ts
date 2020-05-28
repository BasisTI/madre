import { Component, Input } from '@angular/core';
import { FormBuilder, Validators, FormArray } from '@angular/forms';
import { MunicipioService } from './municipio.service';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../../models/dropdowns/opcoes-de-tipo-de-endereco';

@Component({
    selector: 'app-endereco',
    templateUrl: './endereco.component.html',
    styleUrls: ['./endereco.component.scss'],
})
export class EnderecoComponent {
    @Input() enderecos: FormArray;
    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_TELEFONE;

    endereco = this.fb.group({
        municipioId: [null, Validators.required],
        cep: [null, Validators.required],
        uf: [null],
        logradouro: [null, Validators.required],
        numero: [null, Validators.required],
        complemento: [null],
        bairro: [null, Validators.required],
        tipoDoEndereco: [null, Validators.required],
        correspondencia: [null],
    });

    constructor(private fb: FormBuilder, public municipioService: MunicipioService) {}

    adicionarEnderecoALista() {
        this.enderecos.push(this.endereco);
    }
}
