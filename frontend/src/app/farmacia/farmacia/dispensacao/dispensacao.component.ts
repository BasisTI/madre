import { Prescricao } from './prescricao';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-dispensacao',
    templateUrl: './dispensacao.component.html',
    styleUrls: ['./dispensacao.component.css'],
})
export class DispensacaoComponent implements OnInit {
    Prescricao: Prescricao[];

    texts: string[];

    results: string[];

    search(event) {
        // this.mylookupservice.getResults(event.query).then((data) => {
        //     this.results = data;
        // });
        console.log('oi');
    }
    listar() {
        this.service.getPrescricao().subscribe((res) => (this.Prescricao = res));
        console.log(this.Prescricao);
    }

    constructor(private service: FarmaciaService) {}

    ngOnInit() {
        this.service.getPrescricao().subscribe((res) => (this.Prescricao = res));
    }
}
