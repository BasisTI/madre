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
    results = [
        { label: 'Selecione Situação', value: null },
        { label: 'Ativo', value: 'true' },
        { label: 'Inativo', value: 'false' },
    ];
    medicamento: Medicamento[];

    listar() {
        this.service
            .getMedicamentos(this.Codigo, this.Descricao, this.situacao)
            .subscribe((medicamentos) => {
                this.medicamento = medicamentos.content;
            });
        console.log(this.medicamento);
    }

    constructor(private service: FarmaciaService) {}

    ngOnInit(): void {
        this.listar();
    }
}
