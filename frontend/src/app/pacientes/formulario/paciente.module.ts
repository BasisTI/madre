import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { PacienteRoutingModule } from './paciente-routing.module';
import { PacienteComponent } from './paciente.component';
import { PacienteFormComponent } from './paciente-form.component';
import { PacienteListComponent } from './paciente-list.component';
import { PacienteService } from './paciente.service';

import {
    CrudModule,
    CRUD_SERVICE,
    CrudResolveGuard,
    FormNotificationModule,
} from '@nuvem/primeng-components';

import { DatatableModule } from '@nuvem/primeng-components';

import { PRIMENG_IMPORTS } from '../../primeng-imports';
import { PacienteDadosPessoaisFormComponent } from './dados-pessoais/paciente-form-dados-pessoais.component';
import { PacienteResponsavelFormComponent } from './responsavel/paciente-form-responsavel.component';
import { PacienteDocumentosFormComponent } from './documentos/paciente-form-documentos.component';
import { PacienteCertidaoFormComponent } from './certidao/paciente-form-certidao.component';
import { PacienteCartaoSusFormComponent } from './cartao-sus/paciente-form-cartao-sus.component';
import { PacienteTelefoneFormComponent } from './telefone/paciente-form-telefone.component';
import { PacienteEnderecoFormComponent } from './endereco/paciente-form-endereco.component';

import { CalendarMadreComponent } from '../../shared/components/calendar-madre.component';
import { RacaService } from './dados-pessoais/raca.service';
import { EstadoCivilService } from './dados-pessoais/estado-civil.service';
import { NacionalidadeService } from './dados-pessoais/nacionalidade.service';
import { EtniaService } from './dados-pessoais/etnia.service';
import { OcupacaoService } from './dados-pessoais/ocupacao.service';
import { ReligiaoService } from './dados-pessoais/religiao.service';
import { GrauDeParentescoService } from './responsavel/grau-de-parentesco.service';
import { OrgaoEmissorService } from './documentos/orgao-emissor.service';
import { JustificativaService } from './cartao-sus/justificativa.service';
import { MotivoDoCadastroService } from './cartao-sus/motivo-do-cadastro.service';
import { UfService } from './municipio/uf.service';
import { MunicipioService } from './municipio/municipio.service';
import { CepService } from './endereco/cep.service';
import { TabViewModule } from 'primeng/tabview';

@NgModule({
  imports: [
    PRIMENG_IMPORTS,
    CrudModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PacienteRoutingModule,
    HttpClientModule,
    DatatableModule,
    FormNotificationModule,
    TabViewModule
  ],
  declarations: [
    PacienteComponent,
    PacienteFormComponent,
    PacienteListComponent,
    PacienteDadosPessoaisFormComponent,
    PacienteResponsavelFormComponent,
    PacienteDocumentosFormComponent,
    PacienteCertidaoFormComponent,
    PacienteCartaoSusFormComponent,
    PacienteTelefoneFormComponent,
    PacienteEnderecoFormComponent,
    CalendarMadreComponent,
  ],
  providers: [
    PacienteService,
    { provide: CRUD_SERVICE, useExisting: PacienteService },
    CrudResolveGuard,

    RacaService,
    EtniaService,
    EstadoCivilService,
    NacionalidadeService,
    OcupacaoService,
    ReligiaoService,
    GrauDeParentescoService,
    OrgaoEmissorService,
    JustificativaService,
    MotivoDoCadastroService,
    UfService,
    MunicipioService,
    CepService,
  ]
})
export class PacienteModule {}
