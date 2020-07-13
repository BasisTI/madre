import { ConsultaService } from './../../consulta.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

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

    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `2010:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';

    constructor(
        private fb: FormBuilder,
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private consultaService: ConsultaService,
    ) {}

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

        const consultaId = this.route.snapshot.params['id'];

        if (consultaId) {
            this.carregarConsulta(consultaId);
        }
    }

    carregarConsulta(id: number) {
        this.consultaService.buscarConsultaId(id).subscribe((consultas) => {
            this.detalharConsultas = consultas;
        });
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
