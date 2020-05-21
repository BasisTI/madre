import { Component, OnInit } from '@angular/core';

import { EventoCalendario } from '@internacao/models/evento-calendario';
import { EventoLeitoService } from '@internacao/services/evento-leito.service';
import dayGridPlugin from '@fullcalendar/daygrid';

@Component({
    selector: 'app-calendario',
    templateUrl: './calendario.component.html',
})
export class CalendarioComponent implements OnInit {
    events: Array<EventoCalendario>;

    options: any;

    constructor(private eventoLeitoService: EventoLeitoService) {}

    formatarData(data: Date): string {
        const normalize = (x: number): string => (x < 10 ? `0${x}` : `${x}`);
        const dateObjectToFormattedString = (dateObject: Date): string => {
            const year = normalize(dateObject.getFullYear());
            const day = normalize(dateObject.getDate());
            const month = normalize(dateObject.getMonth() + 1);
            return `${year}-${month}-${day}`;
        };

        return dateObjectToFormattedString(data);
    }

    ngOnInit(): void {
        this.options = {
            plugins: [dayGridPlugin],
            defaultDate: this.formatarData(new Date()),
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
}
