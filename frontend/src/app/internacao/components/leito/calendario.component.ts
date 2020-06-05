import * as moment from 'moment';

import { Component, OnDestroy, OnInit } from '@angular/core';

import { BreadcrumbService } from '@nuvem/primeng-components';
import { EventoCalendario } from '@internacao/models/evento-calendario';
import { EventoLeitoService } from '@internacao/services/evento-leito.service';
import dayGridPlugin from '@fullcalendar/daygrid';

@Component({
    selector: 'app-calendario',
    templateUrl: './calendario.component.html',
})
export class CalendarioComponent implements OnInit, OnDestroy {
    events: Array<EventoCalendario>;

    options: any;

    constructor(
        private eventoLeitoService: EventoLeitoService,
        private breadcrumbService: BreadcrumbService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Internação',
            },
            {
                label: 'Leitos',
            },
            {
                label: 'Calendário',
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
        this.eventoLeitoService.obterEventosCalendario().subscribe((eventos) => {
            this.events = eventos;
        });
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
