import { BreadcrumbService } from '@breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
    selector: 'app-reserva-de-leito',
    templateUrl: './reserva-de-leito.component.html',
})
export class ReservaDeLeitoComponent implements OnInit, OnDestroy {
    constructor(private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Internação',
            },
            {
                label: 'Reservar Leito',
                routerLink: 'reserva-de-leito',
            },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
