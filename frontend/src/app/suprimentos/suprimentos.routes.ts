import { ConfirmacaoRecebimentoComponent } from './recebimento/confirmacao-recebimento/confirmacao-recebimento.component';
import { NotaFiscalFormComponent } from './documento-fiscal-entrada/nota-fiscal/nota-fiscal-form.component';
import { RecebimentoFormComponent } from './recebimento/recebimento-form.component';
import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'recebimentos/novo',
        component: RecebimentoFormComponent,
        data: { breadcrumb: 'Gerar Nota de Recebimento' },
    },
    {
        path: 'recebimentos/confirmacao',
        component: ConfirmacaoRecebimentoComponent,
        data: { breadcrumb: 'Confirmar Recebimento' },
    },
    {
        path: 'documentos-fiscais/notas-fiscais/nova',
        component: NotaFiscalFormComponent,
        data: { breadcrumb: 'Gerar Nota Fiscal' },
    },
];
