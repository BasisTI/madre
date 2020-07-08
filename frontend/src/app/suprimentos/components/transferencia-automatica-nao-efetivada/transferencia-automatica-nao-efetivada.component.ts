import { Component, OnInit } from '@angular/core';
import { DatatableClickEvent } from '@nuvem/primeng-components';
import { TransferenciaAutomaticaService } from '@suprimentos/services/transferencia-automatica.service';
import { TransferenciaAutomaticaDTO } from './../../models/transferencia-automatica';

@Component({
    selector: 'app-transferencia-automatica-nao-efetivada',
    templateUrl: './transferencia-automatica-nao-efetivada.component.html',
})
export class TransferenciaAutomaticaNaoEfetivadaComponent implements OnInit {
    public datatableUrl: string;

    constructor(private transferenciaAutomaticaService: TransferenciaAutomaticaService) {}

    ngOnInit(): void {
        this.datatableUrl = `${this.transferenciaAutomaticaService.getResource()}/automaticas/nao-efetivadas`;
    }

    public onDatatableButtonClick(event: DatatableClickEvent): void {
        switch (event.button) {
            case 'efetivar':
                const transferencia = event.selection as TransferenciaAutomaticaDTO;
                this.transferenciaAutomaticaService
                    .efetivarTransferencia(transferencia.id)
                    .subscribe((resposta) => {
                        console.log(resposta);
                    });
                break;
        }
    }
}
