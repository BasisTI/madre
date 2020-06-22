import { AutorizacaoFornecimentoComponent } from './autorizacao-fornecimento/autorizacao-fornecimento.component';
import { CommonModule } from '@angular/common';
import { DocumentoFiscalEntradaComponent } from './documento-fiscal-entrada/documento-fiscal-entrada.component';
import { NgModule } from '@angular/core';
import { RecebimentoFormComponent } from './recebimento/recebimento-form.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '@shared/shared.module';
import { routes } from './suprimentos.routes';
import { NotaFiscalFormComponent } from './nota-fiscal/nota-fiscal-form.component';

@NgModule({
    declarations: [
        DocumentoFiscalEntradaComponent,
        AutorizacaoFornecimentoComponent,
        RecebimentoFormComponent,
        NotaFiscalFormComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class SuprimentosModule {}
