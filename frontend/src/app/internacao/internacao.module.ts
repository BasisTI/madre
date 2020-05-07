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
import { PlanoDeSaudeComponent } from './components/plano-de-saude/plano-de-saude.component';
import { ConvenioDeSaudeComponent } from './components/convenio-de-saude/convenio-de-saude.component';
import { CaraterDaInternacaoComponent } from './components/carater-da-internacao/carater-da-internacao.component';
import { OrigemDaInternacaoComponent } from './components/origem-da-internacao/origem-da-internacao.component';
import { HospitalComponent } from './components/hospital/hospital.component';
import { ProcedenciaComponent } from './components/procedencia/procedencia.component';
import { LocalDeAtendimentoComponent } from './components/local-de-atendimento/local-de-atendimento.component';
import { ModalidadeAssistencialComponent } from './components/modalidade-assistencial/modalidade-assistencial.component';
import { SolicitacoesDeInternacaoComponent } from './components/solicitacoes-de-internacao/solicitacoes-de-internacao.component';
import { PrioridadePipe } from './pipes/prioridade.pipe';
import { ReservaDeLeitoComponent } from './components/reserva-de-leito/reserva-de-leito.component';
import { LeitoComponent } from './components/leito/leito.component';
import { TipoDeReservaDeLeitoComponent } from './components/tipo-de-reserva-de-leito/tipo-de-reserva-de-leito.component';
import { OrigemDaReservaDeLeitoComponent } from './components/origem-da-reserva-de-leito/origem-da-reserva-de-leito.component';

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
        PlanoDeSaudeComponent,
        ConvenioDeSaudeComponent,
        CaraterDaInternacaoComponent,
        OrigemDaInternacaoComponent,
        HospitalComponent,
        ProcedenciaComponent,
        LocalDeAtendimentoComponent,
        ModalidadeAssistencialComponent,
        SolicitacoesDeInternacaoComponent,
        PrioridadePipe,
        ReservaDeLeitoComponent,
        LeitoComponent,
        TipoDeReservaDeLeitoComponent,
        OrigemDaReservaDeLeitoComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class InternacaoModule {}
