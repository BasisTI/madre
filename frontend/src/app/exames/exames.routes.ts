import { FormularioTipoDeMarcacaoComponent } from './components/formulario-tipo-de-marcacao/formulario-tipo-de-marcacao.component';
import { Routes } from '@angular/router';
import { PesquisarAtendimentoComponent } from './components/pesquisar-atendimento/pesquisar-atendimento.component';
import { SolicitarExameComponent } from './components/solicitar-exame/solicitar-exame.component';
import { AtendimentoDiversoComponent } from './components/atendimento-diverso/atendimento-diverso.component';
import { ListaSolicitacoesComponent } from './components/lista-solicitacoes/lista-solicitacoes.component';
import { ListaGradeDeExameComponent } from './components/lista-grade-de-exame/lista-grade-de-exame.component';
import { GradeAgendamentosComponent } from './components/grade-agendamentos/grade-agendamentos.component';
import { ExamesFormComponent } from './components/exames-form/exames-form.component';
import { FormularioCadaverComponent } from './components/formulario-cadaver/formulario-cadaver.component';
import { GuiaMaterialComponent } from './components/guia-material/guia-material.component';
import { AntiCoagulanteComponent } from './components/formulario-anticoagulante/anticoagulante.component';
import { FormularioSalaComponent as FormularioSalaComponent } from './components/formulario-sala/formulario-sala.component';
import { FormularioRecipientesComponent } from './components/formulario-recipientes/formulario-recipientes.component';
import { ListaSalasComponent } from './components/lista-salas/lista-salas.component';
import { GruposDeRecomendacoesDeExamesComponent } from './components/grupos-de-recomendacoes-de-exames/grupos-de-recomendacoes-de-exames.component';

export const routes: Routes = [
    {
        path: 'pesquisar-atendimento',
        component: PesquisarAtendimentoComponent,
    },
    {
        path: 'solicitar-exame',
        component: SolicitarExameComponent,
    },
    {
        path: 'atendimento-diverso',
        component: AtendimentoDiversoComponent,
    },
    {
        path: 'formulario-exame',
        component: ExamesFormComponent,
    },
    {
        path: 'lista-solicitacoes',
        component: ListaSolicitacoesComponent,
    },
    {
        path: 'cadastrar-material',
        component: GuiaMaterialComponent,
    },
    {
        path: 'formulario-recipiente',
        component: FormularioRecipientesComponent,
    },
    {
        path: 'formulario-tipos-de-marcacao',
        component: FormularioTipoDeMarcacaoComponent,
    },
    {
        path: 'anticoagulante',
        component: AntiCoagulanteComponent,
    },
    {
        path: 'manter-grade-agendamento',
        component: GradeAgendamentosComponent,
    },
    {
        path: 'listar-grade-exame',
        component: ListaGradeDeExameComponent,
    },
    {
        path: 'formulario-sala',
        component: FormularioSalaComponent,
    },
    {
        path: 'listar-salas',
        component: ListaSalasComponent,
    },
    {
        path: 'formulario-cadaver',
        component: FormularioCadaverComponent,
    },
    {
        path: 'formulario-salas',
        component: FormularioSalaComponent,
    },
    {
        path: 'grupo-de-recomendacoes-de-exames',
        component: GruposDeRecomendacoesDeExamesComponent,
    },
];
