import { InternacaoDePacienteService } from '@internacao/services/internacao-de-paciente.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Internacao } from '@internacao/models/internacao';
import { Router } from '@angular/router';
import { DatatableClickEvent, DatatableComponent } from '@nuvem/primeng-components';

@Component({
    selector: 'app-formulario-transferir-paciente',
    templateUrl: './formulario-transferir-paciente.component.html',
    styleUrls: ['./formulario-transferir-paciente.component.css'],
})
export class FormularioTransferirPacienteComponent implements OnInit {
    constructor(
        private internacaoDePacienteService: InternacaoDePacienteService,
        private router: Router,
    ) {}

    @ViewChild(DatatableComponent) dataTable: DatatableComponent;

    internacaoDialog: boolean;
    submitted: boolean;
    internacoes: Internacao[];
    internacao: Internacao;

    listarTransferiPaciente() {
        this.internacaoDePacienteService.listarGradeTransferiPaciente().subscribe((response) => {
            this.internacoes = response;
        });
    }
    visualizarDadosTransferir(internacao: Internacao) {
        this.internacao = { ...internacao };
        this.internacaoDialog = true;
    }

    hideDialog() {
        this.internacaoDialog = false;
        this.submitted = false;
    }

    abrirEditarTabela(internacao: Internacao) {
        this.router.navigate(['/internacao/formulario-dados-internacao', internacao.id, 'edit']);
    }

    btnTabela(event: DatatableClickEvent) {
        switch (event.button) {
            case 'edit': {
                this.abrirEditarTabela(event.selection);
               break;
            }
            default: {
                break;
             }
        }
    }

    public onRowDblTabela(event) {
        if (event.target.nodeName === 'TD') {
            this.abrirEditarTabela(this.internacao);
        } else if (event.target.parentNode.nodeName === 'TD') {
            this.abrirEditarTabela(this.internacao);
        }
    }

    ngOnInit(): void {
        this.listarTransferiPaciente();
    }
}
