import { Unidade } from './unidade';
import { Prescricao, Prescricaos } from './prescricao';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import {
    DatatableClickEvent,
    BreadcrumbService,
    DatatablePaginationParameters,
    DatatableComponent,
    PageNotificationService,
} from '@nuvem/primeng-components';
import { ElasticQuery } from 'src/app/shared/elastic-query';

@Component({
    selector: 'app-dispensacao',
    templateUrl: './dispensacao.component.html',
    styleUrls: ['./dispensacao.component.css'],
})
export class DispensacaoComponent implements OnInit {
    paginationParameters: DatatablePaginationParameters;
    @ViewChild(DatatableComponent) dataTable: DatatableComponent;
    tipoPrescricaoSelecionada: Prescricaos = new Prescricaos();

    Prescricao: Prescricao[];
    data: Date;
    nome = '';
    dataInicio = '';
    dataFim = '';
    local = '';
    Prontuario;
    elasticQuery: ElasticQuery = new ElasticQuery();
    texts: string[];

    results: Unidade[];

    formatarData(data: Date): string {
        const normalize = (x: number): string => (x < 10 ? `0${x}` : `${x}`);

        const dateObjectToFormattedString = (dateObject: Date): string => {
            const year = normalize(dateObject.getFullYear());
            const day = normalize(dateObject.getDate());
            const month = normalize(dateObject.getMonth() + 1);

            return `${year}-${month}-${day}`;
        };
        return dateObjectToFormattedString(data);
    }

    listar() {
        if (this.data != null) {
            this.dataInicio = this.formatarData(this.data);
        }
        this.service
            .getPrescricao(this.nome, this.dataInicio, this.local)
            .subscribe((res) => (this.Prescricao = res.content));
        this.dataInicio = '';
    }

    constructor(
        private service: FarmaciaService,
        private breadcrumbService: BreadcrumbService,
        private pageNotificationService: PageNotificationService,
        private router: Router,
        private confirmationService: ConfirmationService,
    ) {}

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    ngOnInit() {
        this.service
            .getPrescricao(this.nome, this.dataInicio, this.local)
            .subscribe((res) => (this.Prescricao = res.content));
        this.listar();
    }

    public limparPesquisa() {
        this.nome = '';
        this.dataInicio = '';
        this.data = null;
        this.listar();
    }

    abrirEditar(tipoPrescricaoSelecionada: Prescricaos) {
        this.router.navigate(['/dispensacao-medica', tipoPrescricaoSelecionada.id, 'edit']);
    }

    abrirVisualizar(tipoPrescricaoSelecionada: Prescricaos) {
        this.router.navigate(['/dispensacaos', tipoPrescricaoSelecionada.id, 'view']);
    }

    confirmDelete(tipoPrescricaoSelecionada: Prescricaos) {
        this.confirmationService.confirm({
            message: 'Tem certeza que deseja excluir o registro?',
            accept: () => {
                this.service.deleteP(tipoPrescricaoSelecionada.id).subscribe(() => {
                    this.pageNotificationService.addDeleteMsg();
                    this.listar();
                });
            },
        });
    }

    btnClick(event: DatatableClickEvent) {
        switch (event.button) {
            case 'view': {
                this.abrirVisualizar(event.selection);
                break;
            }
            case 'delete': {
                this.confirmDelete(event.selection);
            }
            default: {
                break;
            }
        }
    }
}
