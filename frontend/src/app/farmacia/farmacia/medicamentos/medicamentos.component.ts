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
    results = [];
    medicamento: Medicamento[];

    listar() {
        this.service.getMedicamentos(this.Codigo, this.Descricao).subscribe((medicamentos) => {
            this.medicamento = medicamentos.content;
        });
        console.log(this.Descricao);
    }

    constructor(private service: FarmaciaService) {
        this.results = [
            { label: 'Selecione Situação' },
            { label: 'Ativo', value: 'true' },
            { label: 'Inativo', value: 'false' },
        ];
    }

    ngOnInit(): void {
        this.listar();
    }
}
