import { Medicamento, Medicamentos } from './Medicamento';
import { Prescricao } from './../dispensacao/prescricao';
import { FarmaciaService } from './../farmacia.service';
import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DatatableClickEvent, DatatablePaginationParameters, DatatableComponent, PageNotificationService } from '@nuvem/primeng-components';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ElasticQuery } from 'src/app/shared/elastic-query';

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

    constructor(private service: FarmaciaService,
        private router: Router,
        private pageNotificationService: PageNotificationService,
        private confirmationService: ConfirmationService) {
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
            message: "Tem certeza que deseja excluir o registro?",
            accept: () => {
                    this.service.delete(tipoMedicamentoSelecionada.id).subscribe(() => {
                    this.pageNotificationService.addDeleteMsg();
                    this.listar();
                });
            }
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
