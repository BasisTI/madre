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

import { routes } from './pacientes.routes';
import { ListaDePacientesComponent } from './lista-de-pacientes/lista-de-pacientes.component';
import { ProntuarioPipe } from './pipes/prontuario.pipe';
import { CartaoSusPipe } from './pipes/cartao-sus.pipe';
import { PacientesService } from './pacientes.service';

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
    ],
    providers: [PacientesService],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
    exports: [],
})
export class PacientesModule {}
