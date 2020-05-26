import { Unidade } from './../dispensacao/unidade';
import { FormBuilder } from '@angular/forms';
import { TipoMedicamento } from './tipoMedicamento';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit } from '@angular/core';
import { Apresentacao } from './apresentacao';

@Component({
    selector: 'app-cadastro-medicamento',
    templateUrl: './cadastro-medicamento.component.html',
    styleUrls: ['./cadastro-medicamento.component.css'],
})
export class CadastroMedicamentoComponent implements OnInit {
    form = this.fb.group({
        medicamento: [''],
        descricao: [''],
        concentracao: [''],
        unidade: [''],
        apresentacao: [''],
        tipo: [''],
        ativo: [''],
    });

    text: string;

    results = new Array<TipoMedicamento>();
    unidade = new Array<Unidade>();
    apresentacao = new Array<Apresentacao>();
    medicamento;

    cadastrar(form) {
        this.service.cadastrar(form);
    }

    search() {
        this.service.getResult().subscribe((data: Array<TipoMedicamento>) => {
            this.results = data;
        });
    }
    searchUnidade() {
        this.service.getResultUnidade().subscribe((data: Array<Unidade>) => {
            this.unidade = data;
        });
    }
    searchApresentacao() {
        this.service.getResultApresentacao().subscribe((data: Array<Apresentacao>) => {
            this.apresentacao = data;
        });
    }
    handleDropdown(event) {}

    constructor(private service: FarmaciaService, private fb: FormBuilder) {}

    ngOnInit(): void {}
}
