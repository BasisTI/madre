import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-dispensacao',
    templateUrl: './dispensacao.component.html',
    styleUrls: ['./dispensacao.component.css'],
})
export class DispensacaoComponent implements OnInit {
    Prescricao = [
        {
            prontuario: '000000',
            paciente: 'Mateus de Cerqueira',
            localizacao: 'L:0297A',
            dataInicio: '20/02/2020',
            dataFim: '15/03/2020',
        },
    ];

    constructor() {}

    ngOnInit() {}
}
