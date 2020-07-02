import {NotaFiscalFormComponent} from './documento-fiscal-entrada/nota-fiscal/nota-fiscal-form.component';
import {RecebimentoFormComponent} from './recebimento/recebimento-form.component';
import {Routes} from '@angular/router';
import {TransferenciaAutomaticaComponent} from '@suprimentos/transferencia-automatica/transferencia-automatica.component';
import {TransferenciaAutomaticaFormComponent} from "@suprimentos/transferencia-automatica/transferencia-automatica-form.component";

export const routes: Routes = [
    {
        path: 'recebimentos/novo',
        component: RecebimentoFormComponent,
        data: {breadcrumb: 'Gerar Nota de Recebimento'},
    },
    {
        path: 'documentos-fiscais/notas-fiscais/nova',
        component: NotaFiscalFormComponent,
        data: {breadcrumb: 'Gerar Nota Fiscal'},
    },
    {
        path: 'transferencias-automaticas',
        component: TransferenciaAutomaticaComponent,
        data: {breadcrumb: 'Transferências Automáticas'}
    },
    {
        path: 'transferencias-automaticas/nova',
        component: TransferenciaAutomaticaFormComponent,
        data: {breadcrumb: 'Gerar Transferência Automática'}
    }
];
