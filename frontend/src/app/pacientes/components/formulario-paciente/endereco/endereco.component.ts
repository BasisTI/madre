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
        municipioId: ['', Validators.required],
        cep: ['', Validators.required],
        uf: [''],
        logradouro: ['', Validators.required],
        numero: ['', Validators.required],
        complemento: [''],
        bairro: ['', Validators.required],
        tipoDoEndereco: ['', Validators.required],
        correspondencia: ['', Validators.required],
    });

    constructor(private fb: FormBuilder, public municipioService: MunicipioService) {}

    adicionarEnderecoALista() {
        this.enderecos.push(this.endereco);
    }
}
