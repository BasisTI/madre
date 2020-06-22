import { CALENDAR_LOCALE, PageNotificationService } from '@nuvem/primeng-components';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { DocumentoFiscalEntradaService } from '../documento-fiscal-entrada.service';
import { Fornecedor } from '@suprimentos/fornecedor/fornecedor';
import { FornecedorService } from '@suprimentos/fornecedor/fornecedor.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-nota-fiscal-form',
    templateUrl: './nota-fiscal-form.component.html',
})
export class NotaFiscalFormComponent implements OnInit {
    public calendarLocale = CALENDAR_LOCALE;
    public isCpf = false;
    public cpfMask = '999.999.999-99';
    public cnpjMask = '99.999.999/9999-99';
    public fornecedores: Fornecedor[] = [];

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

    constructor(
        private fb: FormBuilder,
        private documentoFiscalEntradaService: DocumentoFiscalEntradaService,
        private fornecedorService: FornecedorService,
        private pageNotificationService: PageNotificationService,
    ) {}

    ngOnInit(): void {}

    public getFornecedoresPorNomeFantasia(nomeFantasia: string): void {
        this.fornecedorService
            .getFornecedoresPorNomeFantasia(nomeFantasia)
            .subscribe((fornecedores) => (this.fornecedores = fornecedores));
    }

    public limparCampoCpfCnpj(): void {
        this.notaFiscalForm.get('cpfCnpj').reset();
    }

    public gravar(): void {
        const dto = {
            ...this.notaFiscalForm.value,
            tipoDocumento: 'NOTA_FISCAL',
            dataGeracao: new Date(),
        };

        for (let prop in dto) {
            if (dto[prop]?.id) {
                dto[prop] = dto[prop].id;
            }
        }

        this.documentoFiscalEntradaService.criarNotaFiscal(dto).subscribe(
            (resposta) => {
                this.notaFiscalForm.reset();
            },
            (err) => {
                if (err.message === 'error.validation' && err.status === 400) {
                    const errors = err.fieldErrors as Array<any>;

                    errors.forEach((fieldErr) => {
                        if (fieldErr.objectName === 'documentoFiscalEntrada') {
                            this.pageNotificationService.addErrorMessage('CPF/CNPJ Inválido');
                            this.notaFiscalForm.get(fieldErr.field).reset();
                            this.notaFiscalForm.get(fieldErr.field).markAsDirty();
                        }
                    });
                }
            },
        );
    }

    public limpar(): void {
        this.notaFiscalForm.reset();
    }
}
