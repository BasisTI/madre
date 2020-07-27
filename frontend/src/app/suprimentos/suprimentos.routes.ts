import { Routes } from '@angular/router';
import { ConsultaEstoqueComponent } from './components/consulta-estoque/consulta-estoque.component';
import { NotaFiscalFormComponent } from './components/documento-fiscal-entrada/nota-fiscal/nota-fiscal-form.component';
import { EfetivacaoTransferenciaAutomaticaComponent } from './components/efetivacao-transferencia-automatica/efetivacao-transferencia-automatica.component';
import { InclusaoSaldoEstoqueComponent } from './components/inclusao-saldo-estoque/inclusao-saldo-estoque.component';
import { RecebimentoFormComponent } from './components/recebimento/recebimento-form.component';
import { RequisicaoMaterialNaoEfetivadaComponent } from './components/requisicao-material-nao-efetivada/requisicao-material-nao-efetivada.component';
import { RequisicaoMaterialFormComponent } from './components/requisicao-material/requisicao-material-form.component';
import { RequisicaoMaterialComponent } from './components/requisicao-material/requisicao-material.component';
import { TransferenciaAutomaticaNaoEfetivadaComponent } from './components/transferencia-automatica-nao-efetivada/transferencia-automatica-nao-efetivada.component';
import { TransferenciaAutomaticaFormComponent } from './components/transferencia-automatica/transferencia-automatica-form.component';
import { TransferenciaAutomaticaComponent } from './components/transferencia-automatica/transferencia-automatica.component';

export const routes: Routes = [
    {
        path: 'consulta-estoque',
        component: ConsultaEstoqueComponent,
        data: { breadcrumb: 'Consultar Estoque' },
    },
    {
        path: 'recebimentos/novo',
        component: RecebimentoFormComponent,
        data: { breadcrumb: 'Gerar Nota de Recebimento' },
    },
    {
        path: 'documentos-fiscais/notas-fiscais/nova',
        component: NotaFiscalFormComponent,
        data: { breadcrumb: 'Gerar Nota Fiscal' },
    },
    {
        path: 'transferencias-automaticas',
        component: TransferenciaAutomaticaComponent,
        data: { breadcrumb: 'Transferências Automáticas' },
    },
    {
        path: 'transferencias-automaticas/nova',
        component: TransferenciaAutomaticaFormComponent,
        data: { breadcrumb: 'Gerar Transferência Automática' },
    },
    {
        path: 'transferencias-automaticas/nao-efetivadas',
        component: TransferenciaAutomaticaNaoEfetivadaComponent,
        data: { breadcrumb: 'Transferências Não Efetivadas' },
    },
    {
        path: 'transferencias-automaticas/nao-efetivadas/:id/efetivar',
        component: EfetivacaoTransferenciaAutomaticaComponent,
        data: { breadcrumb: 'Efetivar Transferência Automática' },
    },
    {
        path: 'requisicoes-materiais',
        component: RequisicaoMaterialComponent,
        data: { breadcrumb: 'Requisição de Materiais' },
    },
    {
        path: 'requisicoes-materiais/nao-efetivadas',
        component: RequisicaoMaterialNaoEfetivadaComponent,
        data: { breadcrumb: 'Requisições Não Efetivadas' },
    },
    {
        path: 'requisicoes-materiais/nova',
        component: RequisicaoMaterialFormComponent,
        data: { breadcrumb: 'Gerar Requisição de Materiais' },
    },
    {
        path: 'inclusao-saldo-estoque',
        component: InclusaoSaldoEstoqueComponent,
        data: { bradcrumb: 'Inclusão Saldo de Estoque' },
    },
];
