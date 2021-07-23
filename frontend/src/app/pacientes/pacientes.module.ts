
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

import { CRUD_SERVICE, PageNotificationModule } from '@nuvem/primeng-components';
import { FormularioTriagemComponent } from './components/triagem/formulario-triagem/formulario-triagem.component';
import { ClassificaoDeRiscoPipe } from './pipes/classificao-de-risco.pipe';
import { ListagemPreCadastroComponent } from './components/listagem-pre-cadastro/listagem-pre-cadastro.component';
import { NivelEmergenciaComponent } from './components/triagem/formulario-triagem/classificacao-de-risco/nivel-emergencia/nivel-emergencia.component';

@NgModule({
    declarations: [
        ProntuarioPipe,
        CartaoSusPipe,
        TriagemComponent,
        ClassificacaoDeRiscoComponent,
        FormularioTriagemComponent,
        ClassificaoDeRiscoPipe,
        ListagemPreCadastroComponent,
        NivelEmergenciaComponent,
    ],
    providers: [
        PacientesService,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes), PageNotificationModule],
    exports: [],
})
export class PacientesModule {}
