import { Sinonimos } from './../../models/subjects/sinonimos';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';

@Component({
    selector: 'app-sinonimos-exames',
    templateUrl: './sinonimos-exames.component.html',
    styleUrls: ['./sinonimos-exames.component.css'],
})
export class SinonimosExamesComponent implements OnInit {
    sinonimo: Sinonimos;
    sinonimos: Sinonimos[] = [];
    situacaoExame = SituacaoAtivo;

    cadastroSinonimoExames = this.fb.group({
        nome: [null, Validators.required],
        situacao: [null, Validators.required],
    });

    constructor(private fb: FormBuilder) {}

    ngOnInit(): void {
        this.cadastroSinonimoExames = this.fb.group({
            nome: [null, Validators.required],
            situacao: [null, Validators.required],
        });
    }

    valid(): boolean {
        return this.cadastroSinonimoExames.valid;
    }

    adicionar() {
        let sinonimoExames = this.cadastroSinonimoExames.value;

        let sinonimoAdicionado: Sinonimos = {
            nome: sinonimoExames.nome,
            situacao: sinonimoExames.situacao,
        };

        this.sinonimos.push(sinonimoAdicionado);

        this.cadastroSinonimoExames.reset();
    }
}
