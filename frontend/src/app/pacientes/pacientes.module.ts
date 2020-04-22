import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared/shared.module';
import { DadosPessoaisComponent } from './components/formulario-paciente/dados-pessoais/dados-pessoais.component';
import { ResponsavelComponent } from './components/formulario-paciente/responsavel/responsavel.component';
import { DocumentosComponent } from './components/formulario-paciente/documentos/documentos.component';
import { CertidaoComponent } from './components/formulario-paciente/certidao/certidao.component';
import { CartaoSusComponent } from './components/formulario-paciente/cartao-sus/cartao-sus.component';
import { TelefoneComponent } from './components/formulario-paciente/telefone/telefone.component';
import { EnderecoComponent } from './components/formulario-paciente/endereco/endereco.component';
import { FormularioCadastroComponent } from './components/formulario-paciente/formulario-cadastro.component';
import { ObservacaoComponent } from './components/formulario-paciente/observacao/observacao.component';
import { TriagemComponent } from './components/triagem/triagem.component';

import { routes } from './pacientes.routes';
import { ListaDePacientesComponent } from './components/lista-de-pacientes/lista-de-pacientes.component';
import { ProntuarioPipe } from './pipes/prontuario.pipe';
import { CartaoSusPipe } from './pipes/cartao-sus.pipe';
import { PacientesService } from './pacientes.service';

import { CRUD_SERVICE } from '@nuvem/primeng-components';
import { RacaService } from './components/formulario-paciente/dados-pessoais/raca.service';
import { EtniaService } from './components/formulario-paciente/dados-pessoais/etnia.service';
import { NacionalidadeService } from './components/formulario-paciente/dados-pessoais/nacionalidade.service';
import { EstadoCivilService } from './components/formulario-paciente/dados-pessoais/estado-civil.service';
import { NaturalidadeService } from './components/formulario-paciente/dados-pessoais/naturalidade.service';
import { OcupacaoService } from './components/formulario-paciente/dados-pessoais/ocupacao.service';
import { ReligiaoService } from './components/formulario-paciente/dados-pessoais/religiao.service';
import { GrauDeParentescoService } from './components/formulario-paciente/responsavel/grau-de-parentesco.service';
import { OrgaoEmissorService } from './components/formulario-paciente/documentos/orgao-emissor.service';
import { JustificativaService } from './components/formulario-paciente/cartao-sus/justificativa.service';
import { MotivoDoCadastro } from './models/dropdowns/types/motivo-do-cadastro';
import { UfService } from './components/formulario-paciente/documentos/uf.service';
import { MunicipioService } from './components/formulario-paciente/endereco/municipio.service';
import { ClassificacaoDeRiscoComponent } from './components/triagem/formulario-triagem/classificacao-de-risco/classificacao-de-risco.component';
import { FormularioTriagemComponent } from './components/triagem/formulario-triagem/formulario-triagem.component';
import { SolicitacaoDeInternacaoComponent } from './components/solicitacao-de-internacao/solicitacao-de-internacao.component';
import { SolicitacaoDeInternacaoService } from './components/solicitacao-de-internacao/solicitacao-de-internacao.service';
import { EspecialidadeService } from './components/solicitacao-de-internacao/especialidade.service';
import { CrmService } from './components/solicitacao-de-internacao/crm.service';
import { ProcedimentoService } from './components/solicitacao-de-internacao/procedimento.service';
import { CidService } from './components/solicitacao-de-internacao/cid.service';
import { EquipeService } from './components/solicitacao-de-internacao/equipe.service';

@NgModule({
    declarations: [
        DadosPessoaisComponent,
        ResponsavelComponent,
        DocumentosComponent,
        CertidaoComponent,
        CartaoSusComponent,
        TelefoneComponent,
        EnderecoComponent,
        FormularioCadastroComponent,
        ObservacaoComponent,
        ListaDePacientesComponent,
        ProntuarioPipe,
        CartaoSusPipe,
        TriagemComponent,
        ClassificacaoDeRiscoComponent,
        FormularioTriagemComponent,
        SolicitacaoDeInternacaoComponent,
    ],
    providers: [
        PacientesService,
        SolicitacaoDeInternacaoService,
        { provide: CRUD_SERVICE, useExisting: RacaService },
        { provide: CRUD_SERVICE, useExisting: EtniaService },
        { provide: CRUD_SERVICE, useExisting: EstadoCivilService },
        { provide: CRUD_SERVICE, useExisting: NacionalidadeService },
        { provide: CRUD_SERVICE, useExisting: NaturalidadeService },
        { provide: CRUD_SERVICE, useExisting: OcupacaoService },
        { provide: CRUD_SERVICE, useExisting: ReligiaoService },
        { provide: CRUD_SERVICE, useExisting: GrauDeParentescoService },
        { provide: CRUD_SERVICE, useExisting: OrgaoEmissorService },
        { provide: CRUD_SERVICE, useExisting: JustificativaService },
        { provide: CRUD_SERVICE, useExisting: MotivoDoCadastro },
        { provide: CRUD_SERVICE, useExisting: UfService },
        { provide: CRUD_SERVICE, useExisting: MunicipioService },
        { provide: CRUD_SERVICE, useExisting: EspecialidadeService },
        { provide: CRUD_SERVICE, useExisting: CrmService },
        { provide: CRUD_SERVICE, useExisting: ProcedimentoService },
        { provide: CRUD_SERVICE, useExisting: CidService },
        { provide: CRUD_SERVICE, useExisting: EquipeService },
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
    exports: [],
})
export class PacientesModule {}
