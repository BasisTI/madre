import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared/shared.module';
import { DadosPessoaisComponent } from './formulario-paciente/dados-pessoais/dados-pessoais.component';
import { ResponsavelComponent } from './formulario-paciente/responsavel/responsavel.component';
import { DocumentosComponent } from './formulario-paciente/documentos/documentos.component';
import { CertidaoComponent } from './formulario-paciente/certidao/certidao.component';
import { CartaoSusComponent } from './formulario-paciente/cartao-sus/cartao-sus.component';
import { TelefoneComponent } from './formulario-paciente/telefone/telefone.component';
import { EnderecoComponent } from './formulario-paciente/endereco/endereco.component';
import { FormularioCadastroComponent } from './formulario-paciente/formulario-cadastro.component';
import { ObservacaoComponent } from './formulario-paciente/observacao/observacao.component';
import { TriagemComponent } from './triagem/triagem.component';

import { routes } from './pacientes.routes';
import { ListaDePacientesComponent } from './lista-de-pacientes/lista-de-pacientes.component';
import { ProntuarioPipe } from './pipes/prontuario.pipe';
import { CartaoSusPipe } from './pipes/cartao-sus.pipe';
import { PacientesService } from './pacientes.service';

import { CRUD_SERVICE } from '@nuvem/primeng-components';
import { RacaService } from './services/raca.service';
import { EtniaService } from './services/etnia.service';
import { NacionalidadeService } from './services/nacionalidade.service';
import { EstadoCivilService } from './services/estado-civil.service';
import { NaturalidadeService } from './services/naturalidade.service';
import { OcupacaoService } from './services/ocupacao.service';
import { ReligiaoService } from './services/religiao.service';
import { GrauDeParentescoService } from './services/grau-de-parentesco.service';
import { OrgaoEmissorService } from './services/orgao-emissor.service';
import { JustificativaService } from './services/justificativa.service';
import { MotivoDoCadastro } from './models/dropdowns/types/motivo-do-cadastro';
import { UfService } from './services/uf.service';
import { MunicipioService } from './services/municipio.service';

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
    ],
    providers: [
        PacientesService,
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
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
    exports: [],
})
export class PacientesModule {}
