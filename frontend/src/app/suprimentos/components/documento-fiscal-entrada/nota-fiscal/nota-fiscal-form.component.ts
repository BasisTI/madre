import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { CALENDAR_LOCALE, PageNotificationService } from '@nuvem/primeng-components';
import { Fornecedor } from '@suprimentos/models/fornecedor';
import { DocumentoFiscalEntradaService } from '@suprimentos/services/documento-fiscal-entrada.service';
import { FornecedorService } from '@suprimentos/services/fornecedor.service';

@Component({
    selector: 'app-nota-fiscal-form',
    templateUrl: './nota-fiscal-form.component.html',
})
export class NotaFiscalFormComponent implements OnInit {
    public calendarLocale = CALENDAR_LOCALE;
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
        cpfCnpj: this.fb.control({ value: null, disabled: true }),
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

    public isCpf(): boolean {
        return this.notaFiscalForm.get('cpfCnpj').value?.length === 11;
    }

    public aoSelecionarFornecedor(fornecedor: Fornecedor) {
        this.notaFiscalForm.get('cpfCnpj').setValue(fornecedor?.cpfCnpj);
    }

    public getFornecedoresPorNomeFantasia(nomeFantasia: string): void {
        this.fornecedorService
            .getFornecedoresPorNomeFantasia(nomeFantasia)
            .subscribe((fornecedores) => (this.fornecedores = fornecedores));
    }

    public limparCampoCpfCnpj(): void {
        this.notaFiscalForm.get('cpfCnpj').reset();
    }

    public gravar(): void {
        this.documentoFiscalEntradaService
            .criarNotaFiscal(this.normalizeForm(this.notaFiscalForm))
            .subscribe((resposta) => {
                this.notaFiscalForm.reset();
            });
    }

    public normalizeForm(form: AbstractControl): { [key: string]: any } {
        const dto = {
            ...form.value,
            tipoDocumento: 'NOTA_FISCAL',
        };

        dto.cpfCnpj = undefined;

        for (let prop in dto) {
            if (dto[prop]?.id) {
                dto[prop] = dto[prop].id;
            }
        }

        return dto;
    }

    public limpar(): void {
        this.notaFiscalForm.reset();
    }
}
