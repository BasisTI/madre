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
import { SinonimosExamesComponent } from './components/sinonimos-exames/sinonimos-exames.component';
import { ExamesFormComponent } from './components/exames-form/exames-form.component';


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
        SinonimosExamesComponent,
        ExamesFormComponent,
        ListarSolicitacoesComponent,
        MaterialExamesComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class ExamesModule { }
