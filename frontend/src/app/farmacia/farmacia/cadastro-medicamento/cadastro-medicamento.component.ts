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

    results = new Array<TipoMedicamento>();
    unidade = new Array<Unidade>();
    apresentacao = new Array<Apresentacao>();

    cadastrar() {
        console.log(this.form.value);
        this.service.cadastrar(this.form.value).subscribe();
    }

    searchTipoMedicamento() {
        this.service.getResultTipoMedicamento().subscribe((data: Array<TipoMedicamento>) => {
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
