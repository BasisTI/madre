import { Component, OnInit } from '@angular/core';
import { Almoxarifado } from './../../models/almoxarifado';
import { ClassificacaoMaterial } from './../../models/classificacao-material';
import { Fornecedor } from './../../models/fornecedor';
import { GrupoMaterial } from './../../models/grupo-material';
import { Material } from './../../models/material';
import { AlmoxarifadoService } from './../../services/almoxarifado.service';
import { ClassificacaoMaterialService } from './../../services/classificacao-material.service';
import { EstoqueAlmoxarifadoService } from './../../services/estoque-almoxarifado.service';
import { FornecedorService } from './../../services/fornecedor.service';
import { GrupoMaterialService } from './../../services/grupo-material.service';
import { MaterialService } from './../../services/material.service';

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

    constructor(
        private estoqueService: EstoqueAlmoxarifadoService,
        private fornecedorService: FornecedorService,
        private materialService: MaterialService,
        private almoxarifadoService: AlmoxarifadoService,
        private grupoMaterialService: GrupoMaterialService,
        private classificacaoMaterialService: ClassificacaoMaterialService,
    ) {}

    ngOnInit(): void {
        this.api = this.estoqueService.getResource();
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
