import { NgModule, ModuleWithProviders } from '@angular/core';

import { JhiDateUtils } from './date-util.service';
import { UnidadeHospitalarService } from '../unidade-hospitalar/unidade-hospitalar.service';
import { UsuarioService } from '../usuario/usuario.service';
import { PreCadastroService } from '../pre-cadastro/pre-cadastro.service';
import { PerfilService } from '../perfil/perfil.service';
import { EspecialidadeService } from '../especialidade/especialidade.service';
import { AnexoService } from '../anexo/anexo.service';
import { TipoPerguntaService } from '../tipo-pergunta/tipo-pergunta.service';
import { PacienteService } from '../paciente/paciente.service';
/* jhipster-needle-add-shared-service-import - JHipster will add shared services imports here */

@NgModule({})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [
        JhiDateUtils,
        UnidadeHospitalarService,
        UsuarioService,
        PreCadastroService,
        PerfilService,
        EspecialidadeService,
        AnexoService,
        TipoPerguntaService,
        PacienteService,
        /* jhipster-needle-add-shared-services - JHipster will add shared services here */
      ]
    };
  }
}
