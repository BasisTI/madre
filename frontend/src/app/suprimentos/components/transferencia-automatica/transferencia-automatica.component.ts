import { Component, OnInit } from '@angular/core';
import { TransferenciaAutomaticaService } from '@suprimentos/services/transferencia-automatica.service';

@Component({
    templateUrl: './transferencia-automatica.component.html',
})
export class TransferenciaAutomaticaComponent implements OnInit {
    public datatableUrl: string;

    constructor(public transferenciaAutomaticaService: TransferenciaAutomaticaService) {}

    ngOnInit(): void {
        this.datatableUrl = `${this.transferenciaAutomaticaService.getResource()}/automaticas`;
    }
}
