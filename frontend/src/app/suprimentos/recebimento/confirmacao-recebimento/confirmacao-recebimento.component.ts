import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { DatatableComponent } from '@nuvem/primeng-components';
import { RecebimentoService } from './../recebimento.service';

@Component({
    selector: 'app-confirmacao-recebimento',
    templateUrl: './confirmacao-recebimento.component.html',
})
export class ConfirmacaoRecebimentoComponent implements OnInit {
    public serverUrl: string;

    public formGroup = this.fb.group({
        id: this.fb.control({ value: null }, Validators.required),
        serie: this.fb.control({ value: '', disabled: true }),
        tipoNotaFiscal: this.fb.control({ value: '', disabled: true }),
        dataEmissao: this.fb.control({ value: '', disabled: true }),
        dataEntrada: this.fb.control({ value: '', disabled: true }),
        valorTotal: this.fb.control({ value: '', disabled: true }),
        valorComprometido: this.fb.control({ value: '', disabled: true }),
    });

    constructor(private fb: FormBuilder, private recebimentoService: RecebimentoService) {}

    ngOnInit(): void {
        this.serverUrl = `${this.recebimentoService.getResource()}-provisorios`;
    }

    public pesquisar(datatable: DatatableComponent): void {
        if (typeof this.formGroup.value.id === 'number') {
            datatable.refresh({ notaFiscalEntradaId: this.formGroup.value.id });
            return;
        }

        datatable.reset();
    }
}
