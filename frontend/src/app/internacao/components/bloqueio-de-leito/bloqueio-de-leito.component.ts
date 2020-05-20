import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Leito } from '@internacao/models/leito';
import { BloqueioDeLeitoService } from '@internacao/services/bloqueio-de-leito.service';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { ConfiguracaoParaCalendarioPrimeNG } from '@shared/p-calendar.config';

@Component({
    selector: 'app-bloqueio-de-leito',
    templateUrl: './bloqueio-de-leito.component.html',
    styleUrls: ['./bloqueio-de-leito.component.scss'],
})
export class BloqueioDeLeitoComponent implements OnInit {
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

    constructor(private fb: FormBuilder, private bloqueioDeLeitoService: BloqueioDeLeitoService) {}

    ngOnInit(): void {}

    aoSelecionarLeito(leito: Leito): void {
        this.formGroup.controls.leito.setValue(leito);
    }

    reservarLeito(): void {
        this.bloqueioDeLeitoService.bloquearLeito(this.formGroup.value).subscribe((resposta) => {
            console.log(resposta);
        });
    }
}
