import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ConfiguracaoParaCalendarioPrimeNG } from '@shared/p-calendar.config';
import { Leito } from '@internacao/models/leito';
import { LiberacaoDeLeitoService } from '@internacao/services/liberacao-de-leito.service';

@Component({
    selector: 'app-liberacao-leito',
    templateUrl: './liberacao-leito.component.html',
    styleUrls: ['./liberacao-leito.component.scss'],
})
export class LiberacaoLeitoComponent implements OnInit, OnDestroy {
    public pCalendarConfig: ConfiguracaoParaCalendarioPrimeNG = {
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yyyy',
        localidade: {
            ...CALENDAR_LOCALE,
            today: 'Agora',
            clear: 'Limpar',
        },
    };

    public formGroup: FormGroup = this.fb.group({
        leito: [null, Validators.required],
        dataDoLancamento: [new Date(), Validators.required],
        justificativa: [''],
    });

    constructor(
        private fb: FormBuilder,
        private liberacaoDeLeitoService: LiberacaoDeLeitoService,
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
                label: 'Liberar Leito',
            },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    aoSelecionarLeito(leito: Leito): void {
        this.formGroup.controls.leito.setValue(leito);
    }

    liberarLeito(): void {
        this.liberacaoDeLeitoService.liberarLeito(this.formGroup.value).subscribe((resposta) => {
            console.log(resposta);
        });
    }
}
