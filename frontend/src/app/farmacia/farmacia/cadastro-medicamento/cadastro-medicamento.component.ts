import { Unidade } from './../dispensacao/unidade';
import { FormBuilder, Validators } from '@angular/forms';
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
        medicamento: ['', Validators.required],
        descricao: ['', Validators.required],
        concentracao: ['', Validators.required],
        unidade: ['', Validators.required],
        apresentacao: ['', Validators.required],
        tipo: ['', Validators.required],
        ativo: ['', Validators.required],
    });

    results = new Array<TipoMedicamento>();
    unidade = new Array<Unidade>();
    apresentacao = new Array<Apresentacao>();

    cadastrar() {
        console.log(this.form.value);
        this.service.cadastrar(this.form.value).subscribe();
    }

    searchTipoMedicamento(event) {
        this.service
            .getResultTipoMedicamento(event.query)
            .subscribe((data: Array<TipoMedicamento>) => {
                this.results = data;
            });
    }
    searchUnidade(event) {
        this.service.getResultUnidade(event.query).subscribe((data: Array<Unidade>) => {
            this.unidade = data;
        });
    }
    searchApresentacao(event) {
        this.service.getResultApresentacao(event.query).subscribe((data: Array<Apresentacao>) => {
            this.apresentacao = data;
        });
    }
    handleDropdown(event) {}

    constructor(private service: FarmaciaService, private fb: FormBuilder) {}

    ngOnInit(): void {
        this.searchApresentacao(event);
        this.searchUnidade(event);
        this.searchTipoMedicamento(event);
    }
}
