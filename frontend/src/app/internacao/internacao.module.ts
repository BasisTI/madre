import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '@shared/shared.module';
import { ArvoreComponent } from './components/cid/arvore-cid/arvore.component';
import { CidComponent } from './components/cid/cid.component';
import { CrmComponent } from './components/crm/crm.component';
import { EquipeComponent } from './components/equipe/equipe.component';
import { EspecialidadeComponent } from './components/especialidade/especialidade.component';
import { ProcedimentoComponent } from './components/procedimento/procedimento.component';
import { SolicitacaoDeInternacaoComponent } from './components/solicitacao-de-internacao/solicitacao-de-internacao.component';
import { routes } from './internacao.routes';
import { CidPipe } from './pipes/cid.pipe';
import { InternacaoDePacienteComponent } from './components/internacao-de-paciente/internacao-de-paciente.component';

@NgModule({
    declarations: [
        SolicitacaoDeInternacaoComponent,
        ArvoreComponent,
        CidPipe,
        EspecialidadeComponent,
        EquipeComponent,
        ProcedimentoComponent,
        CrmComponent,
        CidComponent,
        InternacaoDePacienteComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class InternacaoModule {}
