import { ArvoreComponent } from './components/cid/arvore-cid/arvore.component';
import { BloqueioDeLeitoComponent } from './components/bloqueio-de-leito/bloqueio-de-leito.component';
import { CalendarioComponent } from './components/leito/calendario.component';
import { CaraterDaInternacaoComponent } from './components/carater-da-internacao/carater-da-internacao.component';
import { CidComponent } from './components/cid/cid.component';
import { CidPipe } from './pipes/cid.pipe';
import { CommonModule } from '@angular/common';
import { ConvenioDeSaudeComponent } from './components/convenio-de-saude/convenio-de-saude.component';
import { CrmComponent } from './components/crm/crm.component';
import { EquipeComponent } from './components/equipe/equipe.component';
import { EspecialidadeComponent } from './components/especialidade/especialidade.component';
import { HospitalComponent } from './components/hospital/hospital.component';
import { InternacaoDePacienteComponent } from './components/internacao-de-paciente/internacao-de-paciente.component';
import { LeitoComponent } from './components/leito/leito.component';
import { LiberacaoLeitoComponent } from './components/liberacao-leito/liberacao-leito.component';
import { LocalDeAtendimentoComponent } from './components/local-de-atendimento/local-de-atendimento.component';
import { ModalidadeAssistencialComponent } from './components/modalidade-assistencial/modalidade-assistencial.component';
import { MotivoDoBloqueioDeLeitoComponent } from './components/motivo-do-bloqueio-de-leito/motivo-do-bloqueio-de-leito.component';
import { NgModule } from '@angular/core';
import { OrigemDaInternacaoComponent } from './components/origem-da-internacao/origem-da-internacao.component';
import { OrigemDaReservaDeLeitoComponent } from './components/origem-da-reserva-de-leito/origem-da-reserva-de-leito.component';
import { PacientesListaComponent } from './components/pacientes-lista/pacientes-lista.component';
import { PlanoDeSaudeComponent } from './components/plano-de-saude/plano-de-saude.component';
import { PrioridadePipe } from './pipes/prioridade.pipe';
import { ProcedenciaComponent } from './components/procedencia/procedencia.component';
import { ProcedimentoComponent } from './components/procedimento/procedimento.component';
import { ReservaDeLeitoComponent } from './components/reserva-de-leito/reserva-de-leito.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '@shared/shared.module';
import { SolicitacaoDeInternacaoComponent } from './components/solicitacao-de-internacao/solicitacao-de-internacao.component';
import { SolicitacoesDeInternacaoComponent } from './components/solicitacoes-de-internacao/solicitacoes-de-internacao.component';
import { TipoDeReservaDeLeitoComponent } from './components/tipo-de-reserva-de-leito/tipo-de-reserva-de-leito.component';
import { routes } from './internacao.routes';

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
        BloqueioDeLeitoComponent,
        MotivoDoBloqueioDeLeitoComponent,
        LiberacaoLeitoComponent,
        CalendarioComponent,
        PacientesListaComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class InternacaoModule {}
