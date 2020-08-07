
import { HttpClientModule } from '@angular/common/http';
// tslint:disable-next-line: max-line-length
import { ClassificacaoDeRiscoComponent } from './components/triagem/formulario-triagem/classificacao-de-risco/classificacao-de-risco.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared/shared.module';
import { TriagemComponent } from './components/triagem/triagem.component';

import { routes } from './pacientes.routes';
import { ProntuarioPipe } from './pipes/prontuario.pipe';
import { CartaoSusPipe } from './pipes/cartao-sus.pipe';
import { PacientesService } from './pacientes.service';

import { CRUD_SERVICE } from '@nuvem/primeng-components';
import { FormularioTriagemComponent } from './components/triagem/formulario-triagem/formulario-triagem.component';
import { ClassificaoDeRiscoPipe } from './pipes/classificao-de-risco.pipe';
import { ListagemPreCadastroComponent } from './components/listagem-pre-cadastro/listagem-pre-cadastro.component';

@NgModule({
    declarations: [
        ProntuarioPipe,
        CartaoSusPipe,
        TriagemComponent,
        ClassificacaoDeRiscoComponent,
        FormularioTriagemComponent,
        ClassificaoDeRiscoPipe,
        ListagemPreCadastroComponent,
    ],
    providers: [
        PacientesService,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
    exports: [],
})
export class PacientesModule {}
