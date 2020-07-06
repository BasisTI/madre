import { Component, OnInit } from '@angular/core';
import { TransferenciaAutomaticaService } from '@suprimentos/services/transferencia-automatica.service';

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
}
