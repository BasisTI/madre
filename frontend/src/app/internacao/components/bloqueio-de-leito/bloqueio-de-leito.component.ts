import { Leito } from '@internacao/models/leito';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfiguracaoParaCalendarioPrimeNG } from '@shared/p-calendar.config';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { BloqueioDeLeitoService } from '@internacao/services/bloqueio-de-leito.service';

@Component({
    selector: 'app-bloqueio-de-leito',
    templateUrl: './bloqueio-de-leito.component.html',
    styleUrls: ['./bloqueio-de-leito.component.scss'],
})
export class BloqueioDeLeitoComponent implements OnInit, OnDestroy {
    public pCalendarConfig: ConfiguracaoParaCalendarioPrimeNG = {
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yyyy',
        localidade: CALENDAR_LOCALE
    };

    public formGroup: FormGroup = this.fb.group({
        leito: ['', Validators.required],
        situacao: [{ value: '', disabled: true }, Validators.required],
        motivo: ['', Validators.required],
        dataDoLancamento: [new Date(), Validators.required],
        justificativa: [''],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private bloqueioDeLeitoService: BloqueioDeLeitoService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Internação',
            },
            {
                label: 'Bloquear Leito',
                routerLink: 'bloqueio-de-leito',
            },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    aoSelecionarLeito(leito: Leito): void {
        this.formGroup.controls.situacao.setValue(leito.situacao.nome);
    }

    aoDesfocarLeito(): void {
        this.formGroup.controls.situacao.setValue(null);
    }

    reservarLeito(): void {
        this.bloqueioDeLeitoService.bloquearLeito(this.formGroup.value).subscribe((resposta) => {
            console.log(resposta);
        });
        this.formGroup.reset();
    }
}
