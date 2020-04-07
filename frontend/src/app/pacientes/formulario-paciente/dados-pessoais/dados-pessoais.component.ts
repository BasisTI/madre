import { Component } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-dados-pessoais',
    templateUrl: './dados-pessoais.component.html',
})
export class DadosPessoaisComponent {
    dadosPessoais: FormGroup = this.fb.group({
        nome: [''],
        nomeSocial: [''],
        sexo: [''],
        raca: [''],
        etnia: [''],
        estadoCivil: [''],
        prontuarioMae: [''],
        nomeDaMae: [''],
        nomeDoPai: [''],
        dataDeNascimento: [''],
        horaDoNascimento: [''],
        idade: [''],
        nacionalidade: [''],
        naturalidade: [''],
        uf: [''],
        grauDeInstrucao: [''],
        ocupacao: [''],
        religiao: [''],
        email: [''],
    });

    constructor(private fb: FormBuilder) {}

    onSubmit() {
        console.log(this.dadosPessoais.value);
    }
}
