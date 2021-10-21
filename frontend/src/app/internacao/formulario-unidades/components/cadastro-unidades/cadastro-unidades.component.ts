import { AlmoxarifadosService } from './../../services/almoxarifados.service';

import { CentroService } from './../../services/centro-de-atividade.service';
import { CentroDeAtividade } from './../../models/dropwdowns/centro-de-atividade';

import { UnidadePaiService } from './../../services/unidade-pai.service';
import { UnidadePai } from './../../models/dropwdowns/UnidadePai';
import { Unidade } from './../../models/unidade';

import { Almoxarifado } from './../../models/dropwdowns/almoxarifado';
import { CaracteristicaService } from './../../services/caracteristica.service';
import { Caracteristica } from './../../models/dropwdowns/caracteristicas';
import { OPCOES_DE_SITUACOES } from './../../models/dropwdowns/types/opcoes-de-situacoes';
import { TipoUnidadeService } from './../../services/tipo-unidade.service';
import { TipoUnidade } from './../../models/dropwdowns/TipoUnidade';
import { ClinicaService } from './../../services/clinicas.services';
import { Ala } from '../../models/dropwdowns/Ala';
import { AlaService } from './../../services/ala.service';

import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { Clinica } from '@internacao/formulario-unidades/models/dropwdowns/Clinica';
import { CadastroService } from '@internacao/formulario-unidades/services/cadastro.service';

@Component({
    selector: 'app-cadastro-unidades',
    templateUrl: './cadastro-unidades.component.html',
    styleUrls: ['./cadastro-unidades.component.css'],
})
export class CadastroUnidadesComponent implements OnInit {
    alas: Ala[] = [];
    clinicas: Clinica[] = [];
    tipos: TipoUnidade[] = [];
    caracteristicas: Caracteristica[] = [];
    unidadesPai: UnidadePai[] = [];
    almoxarifados: Almoxarifado[] = [];
    centros: CentroDeAtividade[] = [];
    opcoesDeSitucao = OPCOES_DE_SITUACOES;

    cadastroUnidade = this.fb.group({
        descricao: [null, Validators.required],
        sigla: [null, Validators.required],
        situacao: [null, Validators.required],
        controleDeEstoque: [null],
        idAlmoxarifado: [null],
        andar: [null, Validators.required],
        alaId: [null],
        capacidade: [null],
        clinicaId: [null],
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
        caracteristica: [[null]],
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
        public caracteristicaService: CaracteristicaService,
        public almoxarifadoService: AlmoxarifadosService,
        public centroDeAtividadeService: CentroService,
        public unidadePaiService: UnidadePaiService,
        private cadastroService: CadastroService,
    ) {}

    ngOnInit() {
        this.alaService.getListaDeAlas().subscribe((res) => (this.alas = res));
        this.clinicaService.getListaDeClinicas().subscribe((res) => (this.clinicas = res));
        this.tipoUnidadeService.getListaDeTiposUnidades().subscribe((res) => (this.tipos = res));
        this.caracteristicaService
            .getListaDeCaracteristicas()
            .subscribe((res) => (this.caracteristicas = res));
        this.unidadePaiService.getListaDeUnidadePais().subscribe((res) => (this.unidadesPai = res));
        this.centroDeAtividadeService.getListaDeCentros().subscribe((res) => (this.centros = res));
        this.almoxarifadoService
            .getListaDeAlmoxarifados()
            .subscribe((res) => (this.almoxarifados = res));
    }

    valid(): boolean {
        return this.cadastroUnidade.valid;
    }

    cadastrar() {
        let unidadeFuncional = this.cadastroUnidade.value;
        let pm = this.precricaoMedica.value;
        let pe = this.precricaoEnfermagem.value;
        let cirur = this.cirurgia.value;

        let unidade: Unidade = {
            descricao: unidadeFuncional.descricao,
            sigla: unidadeFuncional.sigla,
            situacao: unidadeFuncional.situacao,
            controleDeEstoque: unidadeFuncional.controleDeEstoque,
            idAlmoxarifado: unidadeFuncional.idAlmoxarifado
                ? unidadeFuncional.idAlmoxarifado.id
                : null,
            alaId: unidadeFuncional.alaId ? unidadeFuncional.alaId.id : null,
            clinicaId: unidadeFuncional.clinicaId ? unidadeFuncional.clinicaId.id : null,
            andar: unidadeFuncional.andar,
            capacidade: unidadeFuncional.capacidade,
            horarioInicio: unidadeFuncional.horarioInicio,
            horarioFim: unidadeFuncional.horarioFim,
            localExame: unidadeFuncional.localExame,
            rotinaDeFuncionamento: unidadeFuncional.rotinaDeFuncionamento,
            anexoDocumento: unidadeFuncional.anexoDocumento,
            setor: unidadeFuncional.setor,
            idCentroDeAtividade: unidadeFuncional.idCentroDeAtividade
                ? unidadeFuncional.idCentroDeAtividade.id
                : null,
            idChefia: unidadeFuncional.idChefia ? unidadeFuncional.idChefia.id : null,
            unidadePaiId: unidadeFuncional.unidadePaiId ? unidadeFuncional.unidadePaiId.id : null,
            tipoUnidadeId: unidadeFuncional.tipoUnidadeId
                ? unidadeFuncional.tipoUnidadeId.id
                : null,
            caracteristicas: unidadeFuncional.caracteristica,
        };

        unidade.prescricaoMedica = {
            horarioValidade: pm.horarioValidade,
            tempoAdiantamento: pm.tempoAdiantamento,
            unidadeTempo: pm.unidadeTempo,
            numeroVias: pm.numeroVias,
        };

        unidade.prescricaoEnfermagem = {
            horarioValidade: pe.horarioValidade,
            tempoAdiantamento: pe.tempoAdiantamento,
            unidadeTempo: pe.unidadeTempo,
            numeroVias: pe.numeroVias,
        };

        unidade.cirurgia = {
            tempoMax: cirur.tempoMax,
            tempoMin: cirur.tempoMin,
            limiteDias: cirur.limiteDias,
            limteDiasConvenios: cirur.limteDiasConvenios,
            intervalocirurgia: cirur.intervalocirurgia,
            intervaloProcedimento: cirur.intervaloProcedimento,
        };

        console.log(unidade);
        this.cadastroService.cadastrarUnidade(unidade).subscribe();
    }
}
