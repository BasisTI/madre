import dayGridPlugin from '@fullcalendar/daygrid';
import { ConsultaService } from '../../consulta.service';
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';
import * as moment from 'moment';
import { FullCalendar } from 'primeng';

import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
@Component({
    selector: 'app-consulta-calendario',
    templateUrl: './consulta-calendario.component.html',
    styleUrls: ['./consulta-calendario.component.scss'],
})
export class ConsultaCalendarioComponent implements OnInit, OnDestroy {
    events: any;
    options: any;
    @ViewChild('daySchedule') fc: FullCalendar;
    consultaId: any;

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
            locale: 'pt-br',
            eventLimit: true,
            plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
            defaultDate: moment().format('YYYY-MM-DD'),
            header: {
                left: 'prev,next',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay',
            },
            editable: true,
            dateClick: (dateClickEvent) => {
                // <-- add the callback here as one of the properties of `options`
                console.log('DATE CLICKED !!!');
            },
        };

        this.consultaService.obterConsultaCalendario().subscribe((eventos) => {
            eventos.forEach((element) => {
                element.url = `#/consulta/detalha-consulta/${element.id}`;
            });
            this.events = eventos;
        });
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
