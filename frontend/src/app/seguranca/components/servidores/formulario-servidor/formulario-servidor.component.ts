import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { CentroAtividadeService } from '@suprimentos/services/centro-atividade.service';
import { OcupacaoService } from 'src/app/pacientes/formulario/dados-pessoais/ocupacao.service';
import { Ocupacao } from 'src/app/pacientes/models/dropdowns/types/ocupacao';
import { OPCOES_DE_TIPO_DE_REMUNERACAO } from 'src/app/seguranca/models/dropdowns/opcoes-de-remuneracao';
import { OPCOES_DE_SITUACOES_SERVIDOR } from 'src/app/seguranca/models/dropdowns/opcoes-de-situacoes';
import { GrupoFuncional } from 'src/app/seguranca/models/dropdowns/grupo-funcional-model';
import { GrupoFuncionalService } from 'src/app/seguranca/services/grupo-funcional.service';
import { LoginService } from 'src/app/seguranca/services/login.service';
import { PessoaService } from 'src/app/seguranca/services/pessoa.service';
import { RamalService } from 'src/app/seguranca/services/ramal.service';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';
import { VinculoService } from 'src/app/seguranca/services/vinculo.service';
import { moment } from 'fullcalendar';
import { Pessoa } from 'src/app/seguranca/models/dropdowns/pessoa-model';
import { Login } from 'src/app/seguranca/models/dropdowns/login-model';
import { Vinculo } from 'src/app/seguranca/models/dropdowns/vinculo-model';
import { Ramal } from 'src/app/seguranca/models/dropdowns/ramal';
import { ListaPessoasServidor } from 'src/app/seguranca/models/dropdowns/lista-pessoa-servidor';
import { ServidorModel } from 'src/app/seguranca/models/servidor-model';
import { ListaVinculosServidor } from 'src/app/seguranca/models/dropdowns/lista-vinculo-servidor';


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

  @Input() formularioServidor: FormGroup;
  localizacao = CALENDAR_LOCALE;
  formatoDeData = 'dd/mm/yy';
  anoAtual = moment().format("YYYY");

  listaPessoasServidor = new Array<ListaPessoasServidor>();
  listaVinculosServidor = new Array<ListaVinculosServidor>();

  idade: number;

  formServidor = this.fb.group({
    id: [''],
    codigo: ['', Validators.required],
    login: [''],
    vinculo: ['', Validators.required],
    matricula: [''],
    codigoStarh: [''],
    inicioDoVinculo: ['', Validators.required],
    fimDoVinculo: [''],
    situacao: [''],
    situacaoDoServidor: [''],
    centroDeAtividadeIdLotacao: ['', Validators.required],
    centroDeAtividadeIdAtuacao: [''],
    ocupacao: ['', Validators.compose([Validators.required])],
    grupoFuncional: [''],
    cargaHoraria: [''],
    tipoDeRemuneracao: [''],
    idade: [''],
    tempoDeContrato: [''],
    funcaoDoCracha: [''],
    chefeDoCentroDeAtividade: [''],
    ramal: [''],
  });

  valid(): boolean {
    return this.formServidor.valid;
  }

  buscaPessoas(event) {
    this.pessoasService.getResultPessoas(event.query).subscribe((data) => {
      this.listaPessoasServidor = data.content;
    });
  }

  buscaVinculos(event) {
    this.vinculoService.getResultVinculo(event.query).subscribe((data) => {
      this.listaVinculosServidor = data.content;
    });
  }

  constructor(private servidorService: ServidorService,
    private pessoasService: PessoaService,
    private loginService: LoginService,
    private vinculoService: VinculoService,
    private centrosService: CentroAtividadeService,
    private ocupacaoService: OcupacaoService,
    private grupoFuncionalService: GrupoFuncionalService,
    private ramalService: RamalService,
    private fb: FormBuilder,
  ) { }

  opcoesDeSituacaoDoServidor = OPCOES_DE_SITUACOES_SERVIDOR;
  opcoesDeTipoDeRemuneracao = OPCOES_DE_TIPO_DE_REMUNERACAO;

  ngOnInit(): void {
    this.pessoasService.getPessoa().subscribe((response) => { this.pessoas = response; this.pessoas.unshift({ id: null, dataDeNascimento: null, nome: "Selecione" }) });
    this.loginService.getUsuario().subscribe((response) => { this.login = response; this.login.unshift({ id: null, login: "Selecione" }) });
    this.vinculoService.getVinculo().subscribe((response) => { this.vinculo = response; this.vinculo.unshift({ id: null, descricao: "Selecione", matricula: null }) });
    this.centrosService.getCentros().subscribe((response) => { this.centros = response; this.centros.unshift({ id: null, descricao: "Selecione" }) });
    this.ocupacaoService.getOcupacoes().subscribe((response) => { this.ocupacao = response; this.ocupacao.unshift({ id: null, valor: "Selecione" }) });
    this.grupoFuncionalService.getGrupoFuncional().subscribe((response) => { this.grupoFuncional = response; this.grupoFuncional.unshift({ id: null, descricao: "Selecione" }) });
    this.ramalService.getRamal().subscribe((response) => { this.ramal = response; this.ramal.unshift({ id: null, numero: "Selecione" }) });
  }

  submit() {
    const form = this.formServidor.value;
    const servidor: ServidorModel = {
      id: form.id,
      codigo: form.codigo?.id,
      matricula: form.matricula,
      codigoStarh: form.codigoStarh,
      inicioDoVinculo: form.inicioDoVinculo,
      fimDoVinculo: form.fimDoVinculo,
      situacao: form.situacao,
      situacaoDoServidor: form.situacaoDoServidor,
      centroDeAtividadeIdLotacao: form.centroDeAtividadeIdLotacao,
      centroDeAtividadeIdAtuacao: form.centroDeAtividadeIdAtuacao,
      ocupacao: form.ocupacao,
      cargaHoraria: form.cargaHoraria,
      tipoDeRemuneracao: form.tipoDeRemuneracao,
      idade: form.idade,
      tempoDeContrato: form.tempoDeContrato,
      funcaoDoCracha: form.funcaoDoCracha,
      chefeDoCentroDeAtividade: form.chefeDoCentroDeAtividade,
      vinculoId: form.vinculo?.id,
      pessoaId: form.codigo?.id,
      ramalId: form.ramal,
      usuarioId: form.login
    };

    if (this.formServidor.value.id != 0) {
      this.servidorService.alterarServidor(servidor).subscribe((e) => {
        this.formServidor.reset();
      });
    } else {
      this.servidorService.cadastrarServidor(servidor).subscribe((e) => {
        this.formServidor.reset();
      });
    }
  }

  calculaIdade(dataDeNascimento: Date) {
    const idade = moment().diff(moment(dataDeNascimento), 'years');
    return idade
  }

  aoSelecionarUmaPessoa(pessoa: Pessoa): void {

    const idade = this.calculaIdade(pessoa.dataDeNascimento);

    this.formServidor.controls['idade'].setValue(idade)
  }

  aoSelecionarUmVinculo(vinculo: Vinculo): void {
    this.formServidor.controls['matricula'].setValue(vinculo.matricula);
  }
}
