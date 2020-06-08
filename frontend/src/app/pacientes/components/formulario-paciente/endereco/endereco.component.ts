import { UF } from 'src/app/pacientes/models/dropdowns/types/uf';
import { UfService } from './../documentos/uf.service';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormArray } from '@angular/forms';
import { MunicipioService } from './municipio.service';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../../models/dropdowns/opcoes-de-tipo-de-endereco';

@Component({
    selector: 'app-endereco',
    templateUrl: './endereco.component.html',
    styleUrls: ['./endereco.component.scss'],
})
export class EnderecoComponent implements OnInit {
    @Input() enderecos: FormArray;
    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_TELEFONE;

    ufs: UF[] = [];

    endereco = this.fb.group({
        municipioId: [null],
        cep: [null, Validators.required],
        uf: [null],
        logradouro: [null, Validators.required],
        numero: [null, Validators.required],
        complemento: [null],
        bairro: [null, Validators.required],
        tipoDoEndereco: [null, Validators.required],
        correspondencia: [null, Validators.required],
    });

    constructor(
        private fb: FormBuilder,
        public municipioService: MunicipioService,
        public ufService: UfService,
    ) {}

    ngOnInit() {
        this.ufService.getListaDeUF().subscribe((res) => (this.ufs = res));
    }

    aoSelecionarUF() {
        console.log('aqui');
    }

    adicionarEnderecoALista() {
        if (this.endereco.valid) {
            this.enderecos.push(this.endereco);
            this.endereco = this.fb.group({
                municipioId: [null, Validators.required],
                cep: [null, Validators.required],
                uf: [null],
                logradouro: [null, Validators.required],
                numero: [null, Validators.required],
                complemento: [null],
                bairro: [null, Validators.required],
                tipoDoEndereco: [null, Validators.required],
                correspondencia: [null, Validators.required],
            });
            this.endereco.reset();
        }
    }
}
