import { FormularioTipoDeMarcacaoComponent } from './components/formulario-tipo-de-marcacao/formulario-tipo-de-marcacao.component';
import { Routes } from '@angular/router';
import { PesquisarAtendimentoComponent } from './components/pesquisar-atendimento/pesquisar-atendimento.component';
import { SolicitarExameComponent } from './components/solicitar-exame/solicitar-exame.component';
import {AtendimentoDiversoComponent} from "./components/atendimento-diverso/atendimento-diverso.component";
import { ListarSolicitacoesComponent } from './components/listar-solicitacoes/listar-solicitacoes.component';
import { ListarGradeDeExameComponent } from './components/listar-grade-de-exame/listar-grade-de-exame.component';
import { GradeAgendamentosComponent } from './components/grade-agendamentos/grade-agendamentos.component';
import { TabelaHorariosAgendadosComponent } from './components/tabela-horarios-agendados/tabela-horarios-agendados.component';
import { ExamesFormComponent } from './components/exames-form/exames-form.component';
import { GuiaMaterialComponent } from './components/guia-material/guia-material.component';
import { AntiCoagulanteComponent } from './components/formulario-anticoagulante/anticoagulante.component';
import { FormularioSalasComponent } from './components/formulario-salas/formulario-salas.component';
import { FormularioRecipientesComponent } from './components/formulario-recipientes/formulario-recipientes.component';
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
        path: 'listar-solicitacoes',
        component: ListarSolicitacoesComponent,
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
        component: GradeAgendamentosComponent
    },
    {
        path: 'listar-grade-exame',
        component: ListarGradeDeExameComponent
    },
    {
        path: 'listar-horarios-agendados',
        component: TabelaHorariosAgendadosComponent
    },
    {
        path: 'formulario-salas',
        component: FormularioSalasComponent
    },
    {
        path: 'grupo-de-recomendacoes-de-exames',
        component: GruposDeRecomendacoesDeExamesComponent
    }
];
