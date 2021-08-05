import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { CentroAtividadeService } from '@suprimentos/services/centro-atividade.service';
import { OcupacaoService } from 'src/app/pacientes/formulario/dados-pessoais/ocupacao.service';
import { Ocupacao } from 'src/app/pacientes/models/dropdowns/types/ocupacao';
import { OPCOES_DE_TIPO_DE_REMUNERACAO } from 'src/app/seguranca/models/dropdowns/opcoes-de-remuneracao';
import { OPCOES_DE_SITUACOES_SERVIDOR } from 'src/app/seguranca/models/dropdowns/opcoes-de-situacoes';
import { GrupoFuncional } from 'src/app/seguranca/models/grupo-funcional-model';
import { ListaPessoasServidor } from 'src/app/seguranca/models/lista-pessoa-servidor';
import { Login } from 'src/app/seguranca/models/login-model';
import { Pessoa } from 'src/app/seguranca/models/pessoa-model';
import { Ramal } from 'src/app/seguranca/models/ramal';
import { Vinculo } from 'src/app/seguranca/models/vinculo-model';
import { GrupoFuncionalService } from 'src/app/seguranca/services/grupo-funcional.service';
import { LoginService } from 'src/app/seguranca/services/login.service';
import { PessoaService } from 'src/app/seguranca/services/pessoa.service';
import { RamalService } from 'src/app/seguranca/services/ramal.service';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';
import { VinculoService } from 'src/app/seguranca/services/vinculo.service';
import { moment } from 'fullcalendar';


@Component({
  selector: 'app-formulario-servidor',
  templateUrl: './formulario-servidor.component.html',
  styleUrls: ['./formulario-servidor.component.css']
})
export class FormularioServidorComponent implements OnInit {

  pessoas: Pessoa[] = [];
  login: Login[] = [];
  vinculo: Vinculo[] = [];
  ramal: Ramal[] = [];
  centros: CentroAtividade[];
  ocupacao: Ocupacao[];
  grupoFuncional: GrupoFuncional[] = [];


  situacaoDoServidor = OPCOES_DE_SITUACOES;

  @Input() 
  formularioServidor: FormGroup;
  localizacao = CALENDAR_LOCALE;
  formatoDeData = 'dd/mm/yy';
  listaPessoasServidor = new Array<ListaPessoasServidor>();
  idade = '';


  buscaPessoas(event) {
    this.pessoasService.getResultPessoas(event.query).subscribe((data) => {
      this.listaPessoasServidor = data.content;
    });
  }

  pessoaSelecionada(evt: any) {
    this.idadePaciente(evt.dataDeNascimento);
  }

  constructor(private servidorService: ServidorService,
              private pessoasService: PessoaService, 
              private loginService: LoginService, 
              private vinculoService: VinculoService,
              private centrosService: CentroAtividadeService,
              private ocupacaoService: OcupacaoService,
              private grupoFuncionalService: GrupoFuncionalService,
              private ramalService: RamalService,
  ) { }

  private idadePaciente(dtNascimento: Date) {
    if (dtNascimento) {
        const idade = moment().diff(moment(dtNascimento), 'years');

        if (idade === 0) {
            this.idade = 'Menos de 1 ano';
            return;
        }

        this.idade = String(idade);

        return;
    }

    this.idade = '';
}

  opcoesDeSituacaoDoServidor = OPCOES_DE_SITUACOES_SERVIDOR;
  opcoesDeTipoDeRemuneracao = OPCOES_DE_TIPO_DE_REMUNERACAO;

  ngOnInit(): void {
    this.pessoasService.getPessoa().subscribe((response) => { this.pessoas = response; });
    this.loginService.getUsuario().subscribe((response) => { this.login = response; });
    this.vinculoService.getVinculo().subscribe((response) => {this.vinculo = response });
    this.centrosService.getCentros().subscribe((response) => { this.centros = response; });
    this.ocupacaoService.getOcupacoes().subscribe((response) => { this.ocupacao = response; });
    this.grupoFuncionalService.getGrupoFuncional().subscribe((response) => {this.grupoFuncional = response});
    this.ramalService.getRamal().subscribe((response) => {this.ramal = response });
  }

}
