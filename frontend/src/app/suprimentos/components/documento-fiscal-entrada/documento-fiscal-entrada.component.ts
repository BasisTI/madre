import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DocumentoFiscalEntrada } from '../../models/documento-fiscal-entrada';
import { DocumentoFiscalEntradaService } from '../../services/documento-fiscal-entrada.service';

@Component({
    selector: 'app-documento-fiscal-entrada',
    templateUrl: './documento-fiscal-entrada.component.html',
})
export class DocumentoFiscalEntradaComponent implements OnInit {
    public documentos: DocumentoFiscalEntrada[];

    @Input() public parentFormGroup: FormGroup;

    constructor(public documentoFiscalEntradaService: DocumentoFiscalEntradaService) {}

    ngOnInit(): void {}

    public obterDocumentoPeloNumero(event: { originalEvent: InputEvent; query: string }): void {
        this.documentoFiscalEntradaService
            .obterDocumentoPeloNumero(event.query)
            .subscribe((documentos) => {
                this.documentos = documentos;
            });
    }

    public limpar(): void {
        this.parentFormGroup.reset();
    }

    public aoSelecionarUmNumero(documento: DocumentoFiscalEntrada): void {
        this.parentFormGroup.controls['id'].setValue(documento.id);
        this.parentFormGroup.controls['serie'].setValue(documento.serie);
        this.parentFormGroup.controls['tipoNotaFiscal'].setValue(documento.tipoDocumentoFiscal);
        this.parentFormGroup.controls['dataEmissao'].setValue(documento.dataEmissao);
        this.parentFormGroup.controls['dataEntrada'].setValue(documento.dataEntrada);
        this.parentFormGroup.controls['valorTotal'].setValue(documento.valorTotal);
    }
}
