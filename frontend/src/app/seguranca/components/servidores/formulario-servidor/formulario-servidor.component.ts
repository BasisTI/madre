import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { CentroAtividadeService } from '@suprimentos/services/centro-atividade.service';
import { OPCOES_DE_TIPO_DE_REMUNERACAO } from 'src/app/seguranca/models/dropdowns/opcoes-de-remuneracao';
import { OPCOES_DE_SITUACOES_SERVIDOR } from 'src/app/seguranca/models/dropdowns/opcoes-de-situacoes';
import { GrupoFuncionalService } from 'src/app/seguranca/services/grupo-funcional.service';
import { LoginService } from 'src/app/seguranca/services/login.service';
import { PessoaService } from 'src/app/seguranca/services/pessoa.service';
import { RamalService } from 'src/app/seguranca/services/ramal.service';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';
import { VinculoService } from 'src/app/seguranca/services/vinculo.service';
import { moment } from 'fullcalendar';
import { Ramal } from 'src/app/seguranca/models/dropdowns/ramal';
import { ListaPessoasServidor } from 'src/app/seguranca/models/dropdowns/lista-pessoa-servidor';
import { ServidorModel } from 'src/app/seguranca/models/servidor-model';
import { ListaVinculosServidor } from 'src/app/seguranca/models/dropdowns/lista-vinculo-servidor';
import { OcupacaoDeCargo } from 'src/app/seguranca/models/dropdowns/ocupacao-de-cargo';
import { OcupacoesDeCargoService } from 'src/app/seguranca/services/ocupacoes-de-cargos.service';
import { Login } from 'src/app/seguranca/models/login-model';
import { Vinculo } from 'src/app/seguranca/models/vinculo-resumo-model';
import { GrupoFuncional } from 'src/app/seguranca/models/grupo-funcional-model';
import { Pessoa } from 'src/app/seguranca/models/pessoa-resumo-model';


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
  ocupacaoDeCargos: OcupacaoDeCargo[];
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
    private ocupacoesDeCargoService: OcupacoesDeCargoService,
    private grupoFuncionalService: GrupoFuncionalService,
    private ramalService: RamalService,
    private fb: FormBuilder,
  ) { }

  opcoesDeSituacaoDoServidor = OPCOES_DE_SITUACOES_SERVIDOR;
  opcoesDeTipoDeRemuneracao = OPCOES_DE_TIPO_DE_REMUNERACAO;

  ngOnInit(): void {
    this.pessoasService.getPessoa().subscribe((response) => { this.pessoas = response; this.pessoas.unshift({ id: null, dataDeNascimento: null, nome: "Selecione", codigo: null }) });
    this.loginService.getUsuario().subscribe((response) => { this.login = response; this.login.unshift({ id: null, login: "Selecione" }) });
    this.vinculoService.getVinculo().subscribe((response) => { this.vinculo = response; this.vinculo.unshift({ id: null, descricao: "Selecione", matricula: null }) });
    this.centrosService.getCentros().subscribe((response) => { this.centros = response; this.centros.unshift({ id: null, descricao: "Selecione" }) });
    this.ocupacoesDeCargoService.getOcupacoesDeCargo().subscribe((response) => { this.ocupacaoDeCargos = response; this.ocupacaoDeCargos.unshift({ id: null, descricao: "Selecione" }) });
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
