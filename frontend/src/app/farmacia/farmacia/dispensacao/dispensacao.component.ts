import { DatatableClickEvent } from '@nuvem/primeng-components';
import { browser } from 'protractor';
import { style } from '@angular/animations';
import { Data, Router } from '@angular/router';
import { Pageable } from './../../../shared/pageable';
import { Unidade } from './unidade';
import { Farmacia } from './../farmacia.router';
import { Prescricao } from './prescricao';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit } from '@angular/core';
import { format } from 'path';
import { FullCalendar } from 'primeng';

@Component({
    selector: 'app-dispensacao',
    templateUrl: './dispensacao.component.html',
    styleUrls: ['./dispensacao.component.css'],
})
export class DispensacaoComponent implements OnInit {
    Prescricao: Prescricao[];
    data: Date;
    nome = '';
    // input = `${this.data.getFullYear()}-${this.data.getMonth()}-${this.data.getDay()}`;
    dataInicio = '';
    dataFim = '';
    local = '';
    Prontuario;

    texts: string[];

    results: Unidade[];

    formatarData(data: Date): string {
        const normalize = (x: number): string => (x < 10 ? `0${x}` : `${x}`);

        const dateObjectToFormattedString = (dateObject: Date): string => {
            const year = normalize(dateObject.getFullYear());
            const day = normalize(dateObject.getDate());
            const month = normalize(dateObject.getMonth() + 1);

            return `${year}-${month}-${day}`;
        };
        return dateObjectToFormattedString(data);
    }

    listar() {
        if (this.data != null) {
            this.dataInicio = this.formatarData(this.data);
        }
        this.service
            .getPrescricao(this.nome, this.dataInicio, this.local)
            .subscribe((res) => (this.Prescricao = res.content));
        this.dataInicio = '';

        console.log(this.Prescricao);
        console.log(this.dataInicio);
    }

    constructor(private service: FarmaciaService, private router: Router) {}

    ngOnInit() {
        // tslint:disable-next-line: max-line-length
        this.service
            .getPrescricao(this.nome, this.dataInicio, this.local)
            .subscribe((res) => (this.Prescricao = res.content));
        console.log(this.Prescricao);
    }
    dispensarMedicamento(event: DatatableClickEvent) {
        this.router.navigate(['/dispensacao-medicamentos', event.selection.id]);
    }
}
