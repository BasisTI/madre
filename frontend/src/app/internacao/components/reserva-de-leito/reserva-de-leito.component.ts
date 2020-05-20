import { ReservaDeLeitoService } from './../../services/reserva-de-leito.service';
import { Leito } from './../../models/leito';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ConfiguracaoParaCalendarioPrimeNG } from '@shared/p-calendar.config';

@Component({
    selector: 'app-reserva-de-leito',
    templateUrl: './reserva-de-leito.component.html',
    styleUrls: ['./reserva-de-leito.component.scss'],
})
export class ReservaDeLeitoComponent implements OnInit {
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

    constructor(private fb: FormBuilder, private reservaDeLeitoService: ReservaDeLeitoService) {}

    ngOnInit(): void {}

    aoSelecionarLeito(leito: Leito): void {
        this.formGroup.controls.leito.setValue(leito);
    }

    reservarLeito(): void {
        this.reservaDeLeitoService.reservarLeito(this.formGroup.value).subscribe((resposta) => {
            console.log(resposta);
        });
    }
}
