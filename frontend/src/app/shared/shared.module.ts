import { NgModule, ModuleWithProviders } from '@angular/core';

import { JhiDateUtils } from './date-util.service';
import { UnidadeHospitalarService } from '../unidade-hospitalar/unidade-hospitalar.service';
import { UsuarioService } from '../usuario/usuario.service';
import { PreCadastroService } from '../pre-cadastro/pre-cadastro.service';
import { PerfilService } from '../perfil/perfil.service';
import { EspecialidadeService } from '../especialidade/especialidade.service';
import { AnexoService } from '../anexo/anexo.service';
import { TipoPerguntaService } from '../tipo-pergunta/tipo-pergunta.service';
import { FuncionalidadeService } from '../funcionalidade/funcionalidade.service';
import { AcaoService } from '../acao/acao.service';
import { Funcionalidade_acaoService } from '../funcionalidade-acao/funcionalidade-acao.service';
import { Perfil_funcionalidade_acaoService } from '../perfil-funcionalidade-acao/perfil-funcionalidade-acao.service';
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
        FuncionalidadeService,
        AcaoService,
        Funcionalidade_acaoService,
        Perfil_funcionalidade_acaoService,
        /* jhipster-needle-add-shared-services - JHipster will add shared services here */
      ]
    };
  }
}
