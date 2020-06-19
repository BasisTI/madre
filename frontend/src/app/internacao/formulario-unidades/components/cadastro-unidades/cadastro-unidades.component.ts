import { TipoUnidadeService } from './../../services/tipo-unidade.service';
import { TipoUnidade } from './../../models/dropwdowns/TipoUnidade';
import { ClinicaService } from './../../services/clinicas.services';
import { Ala } from '../../models/dropwdowns/Ala';
import { AlaService } from './../../services/ala.service';
import { OPCOES_DE_SITUACOES } from './../../models/opcoes-de-situacoes';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { Clinica } from '@internacao/formulario-unidades/models/dropwdowns/Clinica';

@Component({
    selector: 'app-cadastro-unidades',
    templateUrl: './cadastro-unidades.component.html',
    styleUrls: ['./cadastro-unidades.component.css'],
})
export class CadastroUnidadesComponent implements OnInit {
    alas: Ala[] = [];
    clinicas: Clinica[] = [];
    tipos: TipoUnidade[] = [];
    opcoesDeSitucao = OPCOES_DE_SITUACOES;
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

    constructor(
        private fb: FormBuilder,
        public alaService: AlaService,
        public clinicaService: ClinicaService,
        public tipoUnidadeService: TipoUnidadeService,
    ) {}

    ngOnInit() {
        this.alaService.getListaDeAlas().subscribe((res) => (this.alas = res));
        this.clinicaService.getListaDeClinicas().subscribe((res) => (this.clinicas = res));
        this.tipoUnidadeService.getListaDeTiposUnidades().subscribe((res) => (this.tipos = res));
    }

    cadastrar() {
        console.log(this.cadastroUnidade);
    }
}
