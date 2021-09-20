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
import { ListarGradeDeExameComponent } from './components/listar-grade-de-exame/listar-grade-de-exame.component';
import { GradeAgendamentosComponent } from './components/grade-agendamentos/grade-agendamentos.component';
import { FormularioHorariosAgendadosComponent } from './components/formulario-horarios-agendados/formulario-horarios-agendados.component';
import { TabelaHorariosAgendadosComponent } from './components/tabela-horarios-agendados/tabela-horarios-agendados.component';
import { DurationPipe } from './components/exames/pipes/duration.pipe';
import { SinonimosExamesComponent } from './components/sinonimos-exames/sinonimos-exames.component';
import { ExamesFormComponent } from './components/exames-form/exames-form.component';
import { MaterialExamesComponent } from './components/material-exames/material-exames.component';
import { AntiCoagulanteComponent } from './components/anticoagulante/anticoagulante.component';

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
        ListarGradeDeExameComponent,
        GradeAgendamentosComponent,
        FormularioHorariosAgendadosComponent,
        TabelaHorariosAgendadosComponent,
        DurationPipe,
        SinonimosExamesComponent,
        ExamesFormComponent,
        ListarSolicitacoesComponent,
        MaterialExamesComponent,
        AntiCoagulanteComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class ExamesModule { }
