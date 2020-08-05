import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { PacienteRoutingModule } from './paciente-routing.module';
import { PacienteComponent } from './paciente.component';
import { PacienteFormComponent } from './paciente-form.component';
import { PacienteListComponent } from './paciente-list.component';
import { PacienteService } from './paciente.service';

import { CrudModule, CRUD_SERVICE, CrudResolveGuard } from '@nuvem/primeng-components';

import { DatatableModule } from '@nuvem/primeng-components';

import { PRIMENG_IMPORTS } from '../../primeng-imports';
import { PacienteDadosPessoaisFormComponent } from './paciente-form-dados-pessoais.component';
import { PacienteResponsavelFormComponent } from './paciente-form-responsavel.component';
import { PacienteDocumentosFormComponent } from './paciente-form-documentos.component';
import { PacienteCertidaoFormComponent } from './paciente-form-certidao.component';
import { PacienteCartaoSusFormComponent } from './paciente-form-cartao-sus.component';
import { PacienteTelefoneFormComponent } from './paciente-form-telefone.component';
import { PacienteEnderecoFormComponent } from './paciente-form-endereco.component';

import { InputMaskComponent } from '../../shared/components/input-mask.component';
import { CalendarMadreComponent } from '../../shared/components/calendar-madre.component';
import { DropdownComponent } from '../../shared/components/dropdown.component';
import { CheckComponent } from '../../shared/components/check.component';
import { RacaService } from './raca.service';
import { EstadoCivilService } from './estado-civil.service';
import { NacionalidadeService } from './nacionalidade.service';
import { NaturalidadeService } from './naturalidade.service';
import { EtniaService } from './etnia.service';
import { OcupacaoService } from './ocupacao.service';
import { ReligiaoService } from './religiao.service';
import { GrauDeParentescoService } from './grau-de-parentesco.service';
import { OrgaoEmissorService } from './orgao-emissor.service';
import { JustificativaService } from './justificativa.service';
import { MotivoDoCadastroService } from './motivo-do-cadastro.service';
import { UfService } from './uf.service';
import { MunicipioService } from './municipio.service';

@NgModule({
  imports: [ 
    PRIMENG_IMPORTS,
    CrudModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PacienteRoutingModule,
    HttpClientModule,
    DatatableModule
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
    InputMaskComponent,
    CalendarMadreComponent,
    DropdownComponent,
    CheckComponent
  ],
  providers: [ 
    PacienteService,
    { provide: CRUD_SERVICE, useExisting: PacienteService },
    CrudResolveGuard,

    RacaService,
    EtniaService,
    EstadoCivilService,
    NacionalidadeService,
    NaturalidadeService,
    OcupacaoService,
    ReligiaoService,
    GrauDeParentescoService,
    OrgaoEmissorService,
    JustificativaService,
    MotivoDoCadastroService,
    UfService,
    MunicipioService,
  ]
})
export class PacienteModule { }
