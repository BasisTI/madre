import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-cadastro-unidades',
    templateUrl: './cadastro-unidades.component.html',
    styleUrls: ['./cadastro-unidades.component.css'],
})
export class CadastroUnidadesComponent implements OnInit {
    cadastroUnidade = this.fb.group({
        descricao: [null, Validators.required],
        sigla: [null, Validators.required],
        situacao: [null, Validators.required],
        controleDeEstoque: [null],
        idAlmoxarifado: [null],
        andar: [null, Validators.required],
        ala: [null],
        capacidade: [null],
        clinica: [null],
        horarioInicio: [null],
        horarioFim: [null],
        localExame: [null],
        rotinaDeFuncionamento: [null],
        anexoDocumento: [null],
        setor: [null],
        idAlmorifado: [null],
        idCentroDeAtividade: [null, Validators.required],
        tipoUnidadeId: [null],
        unidadePaiId: [null],
        idChefia: [null],
    });

    precricaoMedica = this.fb.group({
        horarioValidade: [null],
        tempoAdiantamento: [null],
        unidadeTempo: [null],
        numeroVias: [null],
    });

    precricaoEnfermagem = this.fb.group({
        horarioValidade: [null],
        tempoAdiantamento: [null],
        unidadeTempo: [null],
        numeroVias: [null],
    });

    cirurgia = this.fb.group({
        tempoMax: [null],
        tempoMin: [null],
        limiteDias: [null],
        limteDiasConvenios: [null],
        intervalocirurgia: [null],
        intervaloProcedimento: [null],
    });

    constructor(private fb: FormBuilder) {}

    ngOnInit(): void {}

    cadastrar() {
        console.log(this.cadastroUnidade);
    }
}
