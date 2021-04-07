import { Medicamento, Medicamentos } from './Medicamento';
import { Prescricao } from './../dispensacao/prescricao';
import { FarmaciaService } from './../farmacia.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { DatatableClickEvent, DatatableComponent, PageNotificationService } from '@nuvem/primeng-components';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ElasticQuery } from 'src/app/shared/elastic-query';

@Component({
    selector: 'app-medicamentos',
    templateUrl: './medicamentos.component.html',
    styleUrls: ['./medicamentos.component.css'],
})
export class MedicamentosComponent implements OnInit {

    @ViewChild(DatatableComponent) datatable: DatatableComponent;

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
        console.log(this.medicamento);
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
        //this.elasticQuery.reset();
        //this.recarregarDataTable();
        this.codigo = '';
        this.descricao = '';
        this.listar();
    }

    public recarregarDataTable() {
        this.datatable.refresh(this.elasticQuery.query);
    }

    abrirEditar(tipoMedicamentoSelecionada: Medicamento) {
        this.router.navigate(['/medicamentos/edit', tipoMedicamentoSelecionada.id]);
    }

    abrirVisualizar(tipoMedicamentoSelecionada: Medicamentos) {
        this.router.navigate(['/medicamentos', tipoMedicamentoSelecionada.id, 'view']);
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
            // case 'delete': {
            //     this.confirmDelete(event.selection);
            //    break;
            // }
            default: {
                break;
             }
        }
    }
    
}
