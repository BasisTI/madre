import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ConfiguracaoParaCalendarioPrimeNG } from '@shared/p-calendar.config';
import { Leito } from './../../models/leito';
import { ReservaDeLeitoService } from './../../services/reserva-de-leito.service';

@Component({
    selector: 'app-reserva-de-leito',
    templateUrl: './reserva-de-leito.component.html',
    styleUrls: ['./reserva-de-leito.component.scss'],
})
export class ReservaDeLeitoComponent implements OnInit, OnDestroy {
    public pCalendarConfig: ConfiguracaoParaCalendarioPrimeNG = {
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yyyy',
        localidade: CALENDAR_LOCALE,
    };

    public formGroup: FormGroup = this.fb.group({
        leito: ['', Validators.required],
        tipoDaReserva: [''],
        origem: [''],
        dataDoLancamento: [new Date(), Validators.required],
        dataInicio: [new Date(), Validators.required],
        dataFim: [null],
        justificativa: [''],
    });

    constructor(
        private fb: FormBuilder,
        private reservaDeLeitoService: ReservaDeLeitoService,
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
                label: 'Reservar Leito',
            },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    aoSelecionarLeito(leito: Leito): void {
        this.formGroup.controls.leito.setValue(leito);
    }

    reservarLeito(): void {
        this.reservaDeLeitoService.reservarLeito(this.formGroup.value).subscribe((resposta) => {
            console.log(resposta);
        });
    }
}
