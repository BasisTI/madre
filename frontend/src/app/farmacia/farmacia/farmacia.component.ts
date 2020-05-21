import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-farmacia',
    templateUrl: './farmacia.component.html',
    styleUrls: ['./farmacia.component.css'],
})
export class FarmaciaComponent implements OnInit {
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
