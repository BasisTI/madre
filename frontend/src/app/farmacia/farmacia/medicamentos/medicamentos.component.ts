import { Medicamento, Medicamentos } from './Medicamento';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
    DatatableClickEvent,
    DatatablePaginationParameters,
    DatatableComponent,
    PageNotificationService,
} from '@nuvem/primeng-components';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ElasticQuery } from 'src/app/shared/elastic-query';
import * as moment from 'moment';

@Component({
    selector: 'app-medicamentos',
    templateUrl: './medicamentos.component.html',
    styleUrls: ['./medicamentos.component.css'],
})
export class MedicamentosComponent implements OnInit {
    paginationParameters: DatatablePaginationParameters;
    @ViewChild(DatatableComponent) dataTable: DatatableComponent;
    tipoMedicamentoSelecionada: Medicamentos = new Medicamentos();

    descricao = '';
    codigo = '';
    situacao = '';
    results = [];
    medicamento: Medicamento[];
    '';
    elasticQuery: ElasticQuery = new ElasticQuery();

    listar() {
        this.service
            .getMedicamentos(this.codigo, this.descricao, this.situacao)
            .subscribe((medicamentos) => {
                this.medicamento = medicamentos.content;
            });
        this.situacao = '';
    }

    constructor(
        private service: FarmaciaService,
        private router: Router,
        private pageNotificationService: PageNotificationService,
        private confirmationService: ConfirmationService,
    ) {
        this.results = [
            { label: 'Selecione Situação' },
            { label: 'Ativo', value: 'true' },
            { label: 'Inativo', value: 'false' },
        ];
    }

    ngOnInit(): void {
        this.listar();
    }

    public limparPesquisa() {
        this.codigo = '';
        this.descricao = '';
        this.listar();
        this.recarregarDataTable();
        this.dataTable.filter();
    }

    public exportarMedicamentos() {
        this.service.exportarMedicamentos().subscribe((x) => {
            const blob = new Blob([x], {
                type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
            });

            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob);
                return;
            }
            const data = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = data;
            let dataAtual: Date = new Date();
            let formattedDate = moment(dataAtual).format('DD-MM-YYYY_HH-mm-ss');
            let nomeArquivo: string = 'medicamentos_' + formattedDate + '.xlsx';

            link.download = nomeArquivo;
            link.dispatchEvent(
                new MouseEvent('click', { bubbles: true, cancelable: true, view: window }),
            );

            setTimeout(function () {
                window.URL.revokeObjectURL(data);
                link.remove;
            }, 100);
        });
    }

    public recarregarDataTable() {
        this.dataTable.refresh(this.elasticQuery.query);
    }

    abrirEditar(tipoMedicamentoSelecionada: Medicamentos) {
        this.router.navigate(['/medicamentos', tipoMedicamentoSelecionada.id, 'edit']);
    }

    abrirVisualizar(tipoMedicamentoSelecionada: Medicamentos) {
        this.router.navigate(['/medicamentos', tipoMedicamentoSelecionada.id, 'view']);
    }

    confirmDelete(tipoMedicamentoSelecionada: Medicamentos) {
        this.confirmationService.confirm({
            message: 'Tem certeza que deseja excluir o registro?',
            accept: () => {
                this.service.delete(tipoMedicamentoSelecionada.id).subscribe(() => {
                    this.pageNotificationService.addDeleteMsg();
                    this.listar();
                });
            },
        });
    }

    btnClick(event: DatatableClickEvent) {
        switch (event.button) {
            case 'edit': {
                this.abrirEditar(event.selection);
                break;
            }
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

    public onRowDblclick(event) {
        if (event.target.nodeName === 'TD') {
            this.abrirEditar(this.tipoMedicamentoSelecionada);
        } else if (event.target.parentNode.nodeName === 'TD') {
            this.abrirEditar(this.tipoMedicamentoSelecionada);
        }
    }
}
