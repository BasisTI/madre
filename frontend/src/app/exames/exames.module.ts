import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PesquisarAtendimentoComponent } from './components/pesquisar-atendimento/pesquisar-atendimento.component';
import { SolicitarExameComponent } from './components/solicitar-exame/solicitar-exame.component';
import { AtendimentoDiversoComponent } from "./components/atendimento-diverso/atendimento-diverso.component";
import { SharedModule } from '@shared/shared.module';
import { RouterModule } from '@angular/router';
import { routes } from './exames.routes';
import { ExameComponent } from './components/exames/components/exame/exame.component';
import { TabelaExamesComponent } from './components/exames/components/tabela-exames/tabela-exames.component';
import { ExamesComponent } from './components/exames/exames.component';
import { FormularioExameComponent } from './components/formulario-exame/formulario-exame.component';
import { ListarSolicitacoesComponent } from './components/listar-solicitacoes/listar-solicitacoes.component';
import { CadastrarMaterialComponent } from './components/cadastrar-material/cadastrar-material.component';
import { FormularioGradeDeAgendamentoComponent } from './components/formulario-grade-de-agendamento/formulario-grade-de-agendamento.component';
import { ListaGradeDeExameComponent } from './components/lista-grade-de-exame/lista-grade-de-exame.component';
import { GradeAgendamentosComponent } from './components/grade-agendamentos/grade-agendamentos.component';
import { ListaHorariosComponent } from './components/lista-horarios/lista-horarios.component';
import { TabelaHorariosAgendadosComponent } from './components/tabela-horarios-agendados/tabela-horarios-agendados.component';
import { DurationPipe } from './components/exames/pipes/duration.pipe';
import { SinonimosExamesComponent } from './components/sinonimos-exames/sinonimos-exames.component';
import { ExamesFormComponent } from './components/exames-form/exames-form.component';
import { MaterialExamesComponent } from './components/material-exames/material-exames.component';
import { FormularioRecipientesComponent } from './components/formulario-recipientes/formulario-recipientes.component';
import { AntiCoagulanteComponent } from './components/formulario-anticoagulante/anticoagulante.component';
import { FormularioSalasComponent } from './components/formulario-salas/formulario-salas.component';
import { FormularioTipoDeMarcacaoComponent } from './components/formulario-tipo-de-marcacao/formulario-tipo-de-marcacao.component';


@NgModule({
    declarations: [
        PesquisarAtendimentoComponent,
        SolicitarExameComponent,
        ExamesComponent,
        AtendimentoDiversoComponent,
        ExameComponent,
        TabelaExamesComponent,
        FormularioExameComponent,
        CadastrarMaterialComponent,
        FormularioGradeDeAgendamentoComponent,
        ListaGradeDeExameComponent,
        GradeAgendamentosComponent,
        ListaHorariosComponent,
        TabelaHorariosAgendadosComponent,
        DurationPipe,
        SinonimosExamesComponent,
        ExamesFormComponent,
        ListarSolicitacoesComponent,
        MaterialExamesComponent,
        FormularioRecipientesComponent,
        AntiCoagulanteComponent,
        FormularioSalasComponent,
        FormularioTipoDeMarcacaoComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class ExamesModule { }
