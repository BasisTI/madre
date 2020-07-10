import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { FormBuilder } from '@angular/forms';

@Component({
    selector: 'app-detalha-consulta',
    templateUrl: './detalha-consulta.component.html',
})
export class DetalhaConsultaComponent implements OnInit, OnDestroy {
    detalharConsultas = this.fb.group({
        dataHoraDaConsulta: [''],
        grade: [''],
        numeroSala: [''],
        turno: [''],
        tipoPagador: [''],
        gradesDisponiveis: [''],
        clinicaCentralId: [''],
        justificativa: [''],
        observacoes: [''],
        pacienteId: [''],
        condicaoDeAtendimentoId: [''],
        formaDeAgendamentoId: [''],
    });

    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Consultas',
                routerLink: 'consulta',
            },
            {
                label: 'Calendário',
                routerLink: 'consulta-calendario',
            },
            {
                label: 'Detalhamento de Consultas',
                routerLink: 'detalha-consulta',
            },
        ]);
    }
    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
