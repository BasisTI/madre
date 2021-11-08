import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  CrudModule, CrudResolveGuard, CRUD_SERVICE, DatatableModule, FormNotificationModule
} from '@nuvem/primeng-components';
import { TabViewModule } from 'primeng/tabview';
import { PRIMENG_IMPORTS } from '../../primeng-imports';
import { CalendarMadreComponent } from '../../shared/components/calendar-madre.component';
import { JustificativaService } from './cartao-sus/justificativa.service';
import { MotivoDoCadastroService } from './cartao-sus/motivo-do-cadastro.service';
import { PacienteCartaoSusFormComponent } from './cartao-sus/paciente-form-cartao-sus.component';
import { PacienteCertidaoFormComponent } from './certidao/paciente-form-certidao.component';
import { EstadoCivilService } from './dados-pessoais/estado-civil.service';
import { EtniaService } from './dados-pessoais/etnia.service';
import { NacionalidadeService } from './dados-pessoais/nacionalidade.service';
import { OcupacaoService } from './dados-pessoais/ocupacao.service';
import { PacienteDadosPessoaisFormComponent } from './dados-pessoais/paciente-form-dados-pessoais.component';
import { RacaService } from './dados-pessoais/raca.service';
import { ReligiaoService } from './dados-pessoais/religiao.service';
import { OrgaoEmissorService } from './documentos/orgao-emissor.service';
import { PacienteDocumentosFormComponent } from './documentos/paciente-form-documentos.component';
import { CepService } from './endereco/cep.service';
import { PacienteEnderecoFormComponent } from './endereco/paciente-form-endereco.component';
import { MunicipioService } from './municipio/municipio.service';
import { UfService } from './municipio/uf.service';
import { PacienteFormComponent } from './paciente-form.component';
import { PacienteListComponent } from './paciente-list.component';
import { PacienteRoutingModule } from './paciente-routing.module';
import { PacienteComponent } from './paciente.component';
import { PacienteService } from './paciente.service';
import { GrauDeParentescoService } from './responsavel/grau-de-parentesco.service';
import { PacienteResponsavelFormComponent } from './responsavel/paciente-form-responsavel.component';
import { TelefoneComponent } from './telefone/telefone.component';







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
    PacienteEnderecoFormComponent,
    CalendarMadreComponent,
    TelefoneComponent,
  ],
  providers: [
    PacienteEnderecoFormComponent,
    CalendarMadreComponent,
    TelefoneComponent,
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
