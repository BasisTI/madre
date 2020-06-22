import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { CALENDAR_LOCALE } from '@nuvem/primeng-components';

@Component({
    selector: 'app-nota-fiscal-form',
    templateUrl: './nota-fiscal-form.component.html',
})
export class NotaFiscalFormComponent implements OnInit {
    public calendarLocale = CALENDAR_LOCALE;
    public isCpf = false;
    public cpfMask = '999.999.999-99';
    public cnpjMask = '99.999.999/9999-99';

    public notaFiscalForm = this.fb.group({
        numeroDocumento: [null, Validators.required],
        serie: [null, Validators.required],
        tipoDocumentoFiscal: [null, Validators.required],
        dataEmissao: [null, Validators.required],
        dataEntrada: [null, Validators.required],
        dataVencimento: [null, Validators.required],
        valorTotal: [null, Validators.required],
        valorComprometido: [null, Validators.required],
        cpfCnpj: [null, Validators.required],
        notaEmpenho: [null],
        observacao: [null],
        fornecedorId: [null],
    });

    constructor(private fb: FormBuilder) {}

    ngOnInit(): void {}

    public limparCampoCpfCnpj(): void {
        this.notaFiscalForm.get('cpfCnpj').reset();
    }

    public gravar(): void {
        console.log(this.notaFiscalForm.value);
    }

    public limpar(): void {
        this.notaFiscalForm.reset();
    }
}
