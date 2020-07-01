import dayGridPlugin from '@fullcalendar/daygrid';
import { ConsultaService } from '../../consulta.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';
import * as moment from 'moment';
import { ConsultaCalendarioModel } from '../../consulta-calendario-model ';

@Component({
    selector: 'app-consulta-calendario',
    templateUrl: './consulta-calendario.component.html',
})
export class ConsultaCalendarioComponent implements OnInit, OnDestroy {
    events: Array<ConsultaCalendarioModel>;
    options: any;

    constructor(
        private consultaService: ConsultaService,
        private breadcrumbService: BreadcrumbService,
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
        ]);
        this.options = {
            plugins: [dayGridPlugin],
            defaultDate: moment().format('YYYY-MM-DD'),
            header: {
                left: 'prev,next',
                center: 'title',
                right: 'month,agendaWeek,agendaDay',
            },
        };
        this.consultaService.obterConsultaCalendario().subscribe((eventos) => {
            this.events = eventos;
        });
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
