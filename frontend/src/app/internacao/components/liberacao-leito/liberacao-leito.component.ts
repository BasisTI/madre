import { Leito } from '@internacao/models/leito';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfiguracaoParaCalendarioPrimeNG, ptBR } from '@shared/p-calendar.config';
import { BreadcrumbService } from '@breadcrumb/breadcrumb.service';
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
            ...ptBR,
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
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private liberacaoDeLeitoService: LiberacaoDeLeitoService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Internação',
            },
            {
                label: 'Liberar Leito',
                routerLink: 'liberacao-de-leito',
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

    liberarLeito(): void {
        this.liberacaoDeLeitoService
            .liberarLeito(this.formGroup.value)
            .subscribe((resposta) => console.log(resposta));
        this.formGroup.reset();
    }
}
