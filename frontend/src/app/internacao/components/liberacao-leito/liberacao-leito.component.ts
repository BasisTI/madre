import { Leito } from '@internacao/models/leito';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { ConfiguracaoParaCalendarioPrimeNG } from '@shared/p-calendar.config';
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
        leito: ['', Validators.required],
        situacao: [{ value: '', disabled: true }, Validators.required],
        dataDoLancamento: [new Date(), Validators.required],
        justificativa: [''],
    });

    constructor(
        private fb: FormBuilder,
        private liberacaoDeLeitoService: LiberacaoDeLeitoService,
    ) {}

    ngOnInit(): void {}

    ngOnDestroy(): void {}

    aoSelecionarLeito(leito: Leito): void {
        this.formGroup.controls.situacao.setValue(leito.situacao.nome);
    }

    aoDesfocarLeito(): void {
        this.formGroup.controls.situacao.setValue(null);
    }

    liberarLeito(): void {
        this.formGroup.reset();
    }
}
