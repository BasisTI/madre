import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { DatatableComponent } from '@nuvem/primeng-components';
import { Almoxarifado } from '@suprimentos/models/almoxarifado';
import { ClassificacaoMaterial } from '@suprimentos/models/classificacao-material';
import { Fornecedor } from '@suprimentos/models/fornecedor';
import { GrupoMaterial } from '@suprimentos/models/grupo-material';
import { Material } from '@suprimentos/models/material';
import { UnidadeMedida } from '@suprimentos/models/unidade-medida';
import { AlmoxarifadoService } from '@suprimentos/services/almoxarifado.service';
import { ClassificacaoMaterialService } from '@suprimentos/services/classificacao-material.service';
import { EstoqueAlmoxarifadoService } from '@suprimentos/services/estoque-almoxarifado.service';
import { FornecedorService } from '@suprimentos/services/fornecedor.service';
import { GrupoMaterialService } from '@suprimentos/services/grupo-material.service';
import { MaterialService } from '@suprimentos/services/material.service';
import { UnidadeMedidaService } from '@suprimentos/services/unidade-medida.service';

@Component({
    selector: 'app-consulta-estoque',
    templateUrl: './consulta-estoque.component.html',
})
export class ConsultaEstoqueComponent implements OnInit {
    public api: string;
    public fornecedores: Fornecedor[] = [];
    public grupos: GrupoMaterial[] = [];
    public almoxarifados: Almoxarifado[] = [];
    public materiais: Material[] = [];
    public classificacoes: ClassificacaoMaterial[] = [];
    public unidadesMedida: UnidadeMedida[] = [];

    @ViewChild('datatable')
    public datatable: DatatableComponent;

    public consultaForm = this.fb.group({
        almoxarifado: [null],
        fornecedor: [null],
        material: [null],
        unidade: [null],
        grupo: [null],
        classificacao: [null],
        estocavel: [null],
        ativo: [null],
    });

    constructor(
        private fb: FormBuilder,
        private estoqueService: EstoqueAlmoxarifadoService,
        private fornecedorService: FornecedorService,
        private materialService: MaterialService,
        private almoxarifadoService: AlmoxarifadoService,
        private grupoMaterialService: GrupoMaterialService,
        private classificacaoMaterialService: ClassificacaoMaterialService,
        private unidadeMedidaService: UnidadeMedidaService,
    ) {}

    ngOnInit(): void {
        this.api = this.estoqueService.getResource();
    }

    public limpar(): void {
        this.consultaForm.reset();
        this.datatable.reset();
    }

    public obterUnidadeMedidaPorDescricao(descricao: string): void {
        this.unidadeMedidaService
            .obterUnidadeMedidaPorDescricao(descricao)
            .subscribe((unidadesMedida) => (this.unidadesMedida = unidadesMedida));
    }

    public getClassificacoesPorDescricao(descricao: string): void {
        this.classificacaoMaterialService
            .getClassificacoesPorDescricao(descricao)
            .subscribe((classificacoes) => {
                this.classificacoes = classificacoes;
            });
    }

    public getFornecedoresPorNomeFantasia(nomeFantasia: string): void {
        this.fornecedorService
            .getFornecedoresPorNomeFantasia(nomeFantasia)
            .subscribe((fornecedores) => (this.fornecedores = fornecedores));
    }

    public obterMateriaisPorNome(nome: string): void {
        this.materialService.obterMateriaisPeloNome(nome).subscribe((materiais) => {
            this.materiais = materiais;
        });
    }

    public obterAlmoxarifadoPorDescricao(descricao: string): void {
        this.almoxarifadoService
            .getAlmoxarifadosPorDescricao(descricao)
            .subscribe((almoxarifados) => {
                this.almoxarifados = almoxarifados;
            });
    }

    public obterGruposPorDescricao(descricao: string): void {
        this.grupoMaterialService.getGruposPorDescricao(descricao).subscribe((grupos) => {
            this.grupos = grupos;
        });
    }
}
