import { Unidade } from './unidade';
import { Farmacia } from './../farmacia.router';
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
    value;
    nome = '';
    dataInicio = '';
    dataFim = '';
    local = '';

    texts: string[];

    results = new Array<Unidade>();

    search(event: { originalEvent: KeyboardEvent; query: string }) {
        this.service.getResult(event.query).subscribe((data) => {
            this.results = data;
        });
    }

    listar() {
        // tslint:disable-next-line: max-line-length
        this.service
            .getPrescricao(this.nome, this.dataInicio, this.local)
            .subscribe((res) => (this.Prescricao = res.content));
        console.log(this.Prescricao);
        console.log(this.nome);
    }

    constructor(private service: FarmaciaService) {}

    ngOnInit() {
        // tslint:disable-next-line: max-line-length
        this.service
            .getPrescricao(this.nome, this.dataInicio, this.local)
            .subscribe((res) => (this.Prescricao = res.content));
    }
}
