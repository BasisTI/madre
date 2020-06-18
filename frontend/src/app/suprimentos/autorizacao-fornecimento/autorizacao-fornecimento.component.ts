import { Component, Input, OnInit } from '@angular/core';

import { AutorizacaoFornecimento } from './autorizacao-fornecimento';
import { AutorizacaoFornecimentoService } from './autorizacao-fornecimento.service';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'app-autorizacao-fornecimento',
    templateUrl: './autorizacao-fornecimento.component.html',
})
export class AutorizacaoFornecimentoComponent implements OnInit {
    public autorizacoes: AutorizacaoFornecimento[];

    @Input() public parentFormGroup: FormGroup;

    constructor(private autorizacaoFornecimentoService: AutorizacaoFornecimentoService) {}

    ngOnInit(): void {}

    obterAutorizacaoFornecimentoPeloNumero(evento: {
        originalEvent: InputEvent;
        query: string;
    }): void {
        this.autorizacaoFornecimentoService
            .obterAutorizacaoFornecimentoPeloNumero(evento.query)
            .subscribe((autorizacoes) => (this.autorizacoes = autorizacoes));
    }

    aoSelecionarUmaAutorizacao(autorizacao: AutorizacaoFornecimento): void {
        this.parentFormGroup.controls['id'].setValue(autorizacao.id);
        this.parentFormGroup.controls['complemento'].setValue(autorizacao.complemento);
        this.parentFormGroup.controls['fornecedor'].setValue(autorizacao.fornecedorNome);
        this.parentFormGroup.controls['tipoItem'].setValue(autorizacao.tipoItem);
    }
}
