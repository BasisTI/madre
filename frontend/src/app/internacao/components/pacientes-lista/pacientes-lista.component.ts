import {
    BreadcrumbService,
    DatatableClickEvent,
    DatatableComponent,
} from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { api } from '@internacao/api';

@Component({
    selector: 'app-pacientes-lista',
    templateUrl: './pacientes-lista.component.html',
})
export class PacientesListaComponent implements OnInit, OnDestroy {
    public readonly uri = `${api}/_search/pacientes`;
    public filtroNomeDoPaciente: string;

    constructor(private router: Router, private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Internação' },
            { label: 'Solicitação de Internação' },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    limpar(datatable: DatatableComponent): void {
        this.filtroNomeDoPaciente = null;
        datatable.reset();
    }

    onButtonClick(evento: DatatableClickEvent): void {
        if (!evento.selection) {
            return;
        }

        if (evento.button === 'create') {
            this.router.navigate(['internacao/solicitacao-de-internacao', evento.selection.id]);
        }
    }
}
