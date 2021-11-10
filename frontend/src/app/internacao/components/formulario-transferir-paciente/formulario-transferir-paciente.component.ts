import { Component, OnInit, ViewChild } from '@angular/core';
import { DatatableClickEvent, DatatableComponent } from '@nuvem/primeng-components';
import { Router } from '@angular/router';
import { FormularioTransferirPacienteService } from '@internacao/services/formulario-transferir-paciente.service';
import { ConfirmationService } from 'primeng/api';
import { ElasticQuery } from '@shared/elastic-query';
import { Internacao } from '@internacao/models/internacao';

@Component({
    selector: 'app-formulario-transferir-paciente',
    templateUrl: './formulario-transferir-paciente.component.html',
    styleUrls: ['./formulario-transferir-paciente.component.css'],
})
export class FormularioTransferirPacienteComponent implements OnInit {
    elasticQuery: ElasticQuery = new ElasticQuery();

    @ViewChild(DatatableComponent) datatable: DatatableComponent;

    constructor(
        private transferirPacienteService: FormularioTransferirPacienteService,
        private router: Router,
        private confirmationService: ConfirmationService,
    ) {}

    internacoes: Internacao[];
    internacaoModal: Internacao[];
    valor = 'Não modelado';

    listarTableTransferiPaciente() {
        this.transferirPacienteService.listarGradeTransferiPaciente().subscribe((response) => {
            this.internacoes = response;
        });
    }

    pesquisar() {
        this.datatable.refresh(this.elasticQuery.query);
    }

    ModalTableClick(event: DatatableClickEvent) {
        if (event.selection) {
            switch (event.button) {
                case 'edit':
                    this.router.navigate(['/internacao/formulario-dados-internacao']);
                    break;
                case 'view':
                    this.internacaoModal = event.selection;
                    break;
                case 'delete':
                    this.confirmationService.confirm({
                        message: 'Você tem certeza que deseja excluir o registro?',
                        accept: () =>
                            this.transferirPacienteService
                                .delete(event.selection.id)
                                .subscribe(() => this.pesquisar()),
                    });
                    break;
            }
        }
    }

    ngOnInit(): void {
        this.listarTableTransferiPaciente();
    }
}
