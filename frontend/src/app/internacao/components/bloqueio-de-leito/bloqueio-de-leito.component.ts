import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { BloqueioDeLeitoService } from '@internacao/services/bloqueio-de-leito.service';
import { ConfiguracaoParaCalendarioPrimeNG } from '@shared/p-calendar.config';
import { Leito } from '@internacao/models/leito';

@Component({
    selector: 'app-bloqueio-de-leito',
    templateUrl: './bloqueio-de-leito.component.html',
    styleUrls: ['./bloqueio-de-leito.component.scss'],
})
export class BloqueioDeLeitoComponent implements OnInit, OnDestroy {
    public pCalendarConfig: ConfiguracaoParaCalendarioPrimeNG = {
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yyyy',
        localidade: CALENDAR_LOCALE,
    };

    public formGroup: FormGroup = this.fb.group({
        leito: ['', Validators.required],
        motivo: ['', Validators.required],
        dataDoLancamento: [new Date(), Validators.required],
        dataInicio: [new Date(), Validators.required],
        dataFim: [null],
        justificativa: [''],
    });

    constructor(
        private fb: FormBuilder,
        private bloqueioDeLeitoService: BloqueioDeLeitoService,
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
                label: 'Bloquear Leito',
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
        this.bloqueioDeLeitoService.bloquearLeito(this.formGroup.value).subscribe((resposta) => {
            console.log(resposta);
        });
    }
}
