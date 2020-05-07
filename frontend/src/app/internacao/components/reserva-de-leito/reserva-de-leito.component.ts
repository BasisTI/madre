import { ReservaDeLeitoService } from './../../services/reserva-de-leito.service';
import { Leito } from './../../models/leito';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BreadcrumbService } from '@breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ConfiguracaoParaCalendarioPrimeNG, ptBR } from '@shared/p-calendar.config';

@Component({
    selector: 'app-reserva-de-leito',
    templateUrl: './reserva-de-leito.component.html',
    styleUrls: ['./reserva-de-leito.component.scss'],
})
export class ReservaDeLeitoComponent implements OnInit, OnDestroy {
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
        tipoDaReserva: [''],
        origem: [''],
        dataDoLancamento: [new Date(), Validators.required],
        justificativa: [''],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private reservaDeLeitoService: ReservaDeLeitoService,
    ) {}

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

    aoSelecionarLeito(leito: Leito): void {
        this.formGroup.controls.situacao.setValue(leito.situacao.nome);
    }

    aoDesfocarLeito(): void {
        this.formGroup.controls.situacao.setValue(null);
    }

    reservarLeito(): void {
        this.reservaDeLeitoService
            .reservarLeito(this.formGroup.value)
            .subscribe((resposta) => console.log(resposta));
        this.formGroup.reset();
    }
}
