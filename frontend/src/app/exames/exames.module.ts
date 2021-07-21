import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PesquisarAtendimentoComponent } from './components/pesquisar-atendimento/pesquisar-atendimento.component';
import { SolicitarExameComponent } from './components/solicitar-exame/solicitar-exame.component';
import { AtendimentoDiversoComponent } from "./components/atendimento-diverso/atendimento-diverso.component";
import { ExamesComponent } from "./components/exames/exames.component";
import { SharedModule } from '@shared/shared.module';
import { RouterModule } from '@angular/router';
import { routes } from './exames.routes';
import { ExameComponent } from './components/exames/components/exame/exame.component';
import { SituacaoExameComponent } from './components/exames/components/situacao-exame/situacao-exame.component';
import { TabelaExamesComponent } from './components/exames/components/tabela-exames/tabela-exames.component';



@NgModule({
    declarations: [
        PesquisarAtendimentoComponent,
        SolicitarExameComponent,
        ExamesComponent,
        AtendimentoDiversoComponent,
        ExameComponent,
        SituacaoExameComponent,
        TabelaExamesComponent

    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class ExamesModule { }
