import { Medicamento } from './Medicamento';
import { Prescricao } from './../dispensacao/prescricao';
import { FarmaciaService } from './../farmacia.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-medicamentos',
    templateUrl: './medicamentos.component.html',
    styleUrls: ['./medicamentos.component.css'],
})
export class MedicamentosComponent implements OnInit {
    Descricao: string;
    Codigo: string;
    situacao: string;
    results = [{ nome: 'true' }, { nome: 'false' }];
    medicamento: Medicamento[];

    lisatr() {
        this.service
            .getMedicamentos(this.Codigo, this.Descricao, this.situacao)
            .subscribe((medicamento) => {
                this.medicamento = medicamento.content;
            });
    }

    constructor(private service: FarmaciaService) {}

    ngOnInit(): void {
        this.service
            .getMedicamentos(this.Codigo, this.Descricao, this.situacao)
            .subscribe((medicamento) => {
                this.medicamento = medicamento.content;
            });
    }
}
