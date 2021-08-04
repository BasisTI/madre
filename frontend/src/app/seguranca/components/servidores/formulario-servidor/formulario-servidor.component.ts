import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { CentroAtividadeService } from '@suprimentos/services/centro-atividade.service';
import { OcupacaoService } from 'src/app/pacientes/formulario/dados-pessoais/ocupacao.service';
import { Ocupacao } from 'src/app/pacientes/models/dropdowns/types/ocupacao';
import { OPCOES_DE_SITUACOES_SERVIDOR } from 'src/app/seguranca/models/dropdowns/opcoes-de-situacoes';
import { Login } from 'src/app/seguranca/models/login-model';
import { Pessoa } from 'src/app/seguranca/models/pessoa-model';
import { Vinculo } from 'src/app/seguranca/models/vinculo-model';
import { LoginService } from 'src/app/seguranca/services/login.service';
import { PessoaService } from 'src/app/seguranca/services/pessoa.service';
import { VinculoService } from 'src/app/seguranca/services/vinculo.service';

@Component({
  selector: 'app-formulario-servidor',
  templateUrl: './formulario-servidor.component.html',
  styleUrls: ['./formulario-servidor.component.css']
})
export class FormularioServidorComponent implements OnInit {

  pessoas: Pessoa[] = [];
  login: Login[] = [];
  vinculo: Vinculo[] = [];
  centros: CentroAtividade[];
  ocupacao: Ocupacao[];


  situacaoDoServidor = OPCOES_DE_SITUACOES;

  @Input() formularioServidor: FormGroup;
  localizacao = CALENDAR_LOCALE;
  formatoDeData = 'dd/mm/yy';

  constructor(private pessoasService: PessoaService, 
              private loginService: LoginService, 
              private vinculoService: VinculoService,
              private centrosService: CentroAtividadeService,
              private ocupacaoService: OcupacaoService,
  ) { }

  opcoesDeSituacaoDoServidor = OPCOES_DE_SITUACOES_SERVIDOR;

  ngOnInit(): void {
    this.pessoasService.getPessoa().subscribe((response) => { this.pessoas = response; });
    this.loginService.getUsuario().subscribe((response) => { this.login = response; });
    this.vinculoService.getVinculo().subscribe((response) => {this.vinculo = response });
    this.centrosService.getCentros().subscribe((response) => { this.centros = response; });
    this.ocupacaoService.getOcupacoes().subscribe((response) => { this.ocupacao = response; });

  }

}
