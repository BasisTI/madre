import { Component, OnInit, ViewChild } from '@angular/core';
import { SituationDropdown } from '../../models/dropdowns/situation.dropdown';
import { ExamModel } from '../../models/subjects/exames-model';
import { GruposExamesService } from '../../services/grupos-exames.service';
import { ExamesService } from '../../services/exames.service';
import { GrupoModel } from '../../models/subjects/grupo-model';
import { FormBuilder, Validators } from '@angular/forms';
import { ItemSolicitacaoExame } from '../../models/subjects/item-solicitacao-exame';
import { ExameComponent } from './components/exame/exame.component';
import { TabelaExamesComponent } from './components/tabela-exames/tabela-exames.component';
@Component({
    selector: 'app-exames',
    templateUrl: './exames.component.html',
    styleUrls: ['./exames.component.scss'],
})
export class ExamesComponent implements OnInit {
    itemsSolicitacaoExame: ItemSolicitacaoExame[] = [];
    alocarItemsSolicitacaoExame: ItemSolicitacaoExame[] = [];
    exames: ExamModel[] = [];
    examesSelecionados: ExamModel[] = [];
    flag: boolean = false;

    @ViewChild(ExameComponent) appExame: ExameComponent;

    @ViewChild(TabelaExamesComponent) appTabela: TabelaExamesComponent;

    situationDropdown = SituationDropdown;
    selectedExamID: number;

    group: String = null;
    groups: GrupoModel[];

    constructor(
        private gruposExamesService: GruposExamesService,
        private examesService: ExamesService,
        private fb: FormBuilder,
    ) {}

    itemSolicitacaoPorExame = this.fb.group({
        urgente: [false, Validators.required],
        dataProgramada: [new Date(), Validators.required],
        situacao: [null, Validators.required],
    });

    itemSolicitacaoPorLote = this.fb.group({
        urgente: [false, Validators.required],
        dataProgramada: [new Date(), Validators.required],
        situacao: [null, Validators.required],
    });

    ngOnInit(): void {
        this.gruposExamesService.GetGrupos().subscribe((response) => {
            this.groups = response;
        });
        this.examesService.getExames().subscribe((response) => {
            this.exames = response;
        });
    }

    validarPorExame() {
        if (this.itemSolicitacaoPorExame.valid && this.selectedExamID) {
            return true;
        }
        return false;
    }

    validarPorLote() {
        if (this.itemSolicitacaoPorLote.valid) {
            return true;
        }
        return false;
    }

    cadastrarPorExame() {
        let item: ItemSolicitacaoExame = {
            urgente: this.itemSolicitacaoPorExame.value.urgente,
            dataProgramada: this.itemSolicitacaoPorExame.value.dataProgramada,
            situacao: this.itemSolicitacaoPorExame.value.situacao,
            itemSolicitacaoExameId: this.selectedExamID,
        };
        this.alocarItemsSolicitacaoExame.push(item);
        this.itemsSolicitacaoExame = this.alocarItemsSolicitacaoExame;
        this.alocarItemsSolicitacaoExame = [];
        this.flag = !this.flag;
    }

    cadastrarPorLote() {
        this.examesSelecionados = this.appExame.pegarExames();

        this.examesSelecionados.forEach((exame) => {
            let item: ItemSolicitacaoExame = {
                urgente: this.itemSolicitacaoPorLote.value.urgente,
                dataProgramada: this.itemSolicitacaoPorLote.value.dataProgramada,
                situacao: this.itemSolicitacaoPorLote.value.situacao,
                itemSolicitacaoExameId: exame.id,
            };

            this.alocarItemsSolicitacaoExame.push(item);
        });
        this.itemsSolicitacaoExame = this.alocarItemsSolicitacaoExame;
        this.alocarItemsSolicitacaoExame = [];
    }

    pegarItensSolicitacaoExame(): any[] {
        if (this.itemsSolicitacaoExame.length >= 1) {
            return this.appTabela.pegarItemsTratados();
        }

        return null;
    }

    buscarExame(event) {
        this.examesService.getExamesPorNomeSinonimo(event.query).subscribe((response) => {
            this.exames = response;
        });
    }

    aoSelecionarExame(event) {
        this.selectedExamID = event.id;
    }
}
