import { Component, Input } from '@angular/core';

import { OPCAO_SELECIONE } from '../../models/dropdowns/opcao-selecione';
import { GrauDeParentescoService } from "./grau-de-parentesco.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { DDDService } from "../telefone/ddd.service";
import { DDD } from "../../models/dropdowns/types/DDD";
import { OPCOES_DE_TIPO_DE_TELEFONE } from "../../models/dropdowns/opcoes-de-tipo-de-telefone";
import { Telefone } from "../paciente.model";
import { PacienteValidators } from "./../paciente.validators";


@Component({
    selector: 'paciente-form-responsavel',
    templateUrl: './paciente-form-responsavel.component.html',
    styleUrls: ['paciente-form-responsavel.component.css'],
})
export class PacienteResponsavelFormComponent {
    @Input() formGroup: FormGroup;

    opcoesDeGrauDeParentesco = [OPCAO_SELECIONE];

    listaDDD = new Array<DDD>();

    tiposDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    tipoDeTelefoneSelecionado: string;

    mascara: string = '9999-9999';

    telefone = this.fb.group({
        id: [''],
        ddd: ['', Validators.required],
        numero: ['', Validators.required],
        tipo: [''],
        observacao: [''],
        indice: [''],
    });

    constructor(
        public grauDeParentescoService: GrauDeParentescoService,
        private DDDService: DDDService,
        private fb: FormBuilder) {
    }

    ngOnInit(): void {
        this.grauDeParentescoService.getListaDeGrausDeParentesco().subscribe((dados) => {
            this.opcoesDeGrauDeParentesco = [
                ...this.opcoesDeGrauDeParentesco,
                ...dados.map((opcao) => {
                    return {
                        label: opcao.valor,
                        value: opcao,
                    };
                }),
            ];
        });
    }

    buscaDDD(event) {
        this.DDDService.getResultDDD(event.query).subscribe((data) => {
            this.listaDDD = data;
        })
    }

    tipoDeMascara(event) {
        this.tipoDeTelefoneSelecionado = event.value;
        if (this.tipoDeTelefoneSelecionado === 'CELULAR') {
            this.mascara = '9 9999-9999';
        }else{
            this.mascara = '9999-9999'
        }
    }

    adicionarTelefoneALista() {
        const form = this.telefone.value;
        const validate = PacienteValidators.validateTelefone(form);
        if(!validate.required){
            this.telefone.patchValue({ indice: this.formGroup.value.responsavel.telefones.length });
            const telefone: Telefone = {
                id: form.id,
                ddd: form.ddd.valor,
                numero: form.numero,
                tipo: form.tipo,
                observacao: form.observacao,
            };
            this.formGroup.value.responsavel.telefones.unshift(telefone);
            this.telefone.reset();
        }
    }

}
