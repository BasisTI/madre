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

    texts: string[];

    results: string[];

    search(event) {
        // this.mylookupservice.getResults(event.query).then((data) => {
        //     this.results = data;
        // });
        console.log('oi');
    }
    listar() {
        console.log(this.Prescricao.values);
    }

    constructor() {}

    ngOnInit() {}
}
