import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ListaInstituicao } from 'src/app/seguranca/models/dropdowns/lista-instituicao';
import { ListaServidor } from 'src/app/seguranca/models/dropdowns/lista-servidor';
import { ListaTiposDeQualificacao } from 'src/app/seguranca/models/dropdowns/lista-tipo-de-qualificacao';
import { OPCOES_DE_SITUACOES_GRADUACAO } from 'src/app/seguranca/models/dropdowns/opcoes-de-situacoes-graduacao';
import { GraduacaoModel } from 'src/app/seguranca/models/graduacao-model';
import { Pessoa } from 'src/app/seguranca/models/pessoa-resumo-model';
import { ServidorModel } from 'src/app/seguranca/models/servidor-model';
import { Servidor } from 'src/app/seguranca/models/servidor-resumo-model';
import { Vinculo } from 'src/app/seguranca/models/vinculo-resumo-model';
import { GraduacaoService } from 'src/app/seguranca/services/graduacao.service';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';
import { TiposDeQualificaoService } from 'src/app/seguranca/services/tipo-de-qualificacao.service';
import { InstituicaoService } from 'src/app/seguranca/services/tipo-instituicao.service';

@Component({
  selector: 'app-formulario-graduacoes',
  templateUrl: './formulario-graduacoes.component.html',
  styleUrls: ['./formulario-graduacoes.component.css']
})
export class FormularioGraduacoesComponent implements OnInit {

  constructor(
    private tiposDeQualificaoService: TiposDeQualificaoService,
    private instituicaoService: InstituicaoService,
    private fb: FormBuilder,
    private graduacaoService: GraduacaoService,
    private servidorService: ServidorService,
  ) { }

  @Input() formularioGraducao: FormGroup;
  listaTiposDeQualificao = new Array<ListaTiposDeQualificacao>();
  listaInstituicao = new Array<ListaInstituicao>();
  situacaoDaGraduacao = OPCOES_DE_SITUACOES_GRADUACAO;
  listaServidor = new Array<ListaServidor>();


  formGraduacao = this.fb.group({
    servidor: [''],
    vinculo: [''],
    nome: [''],
    pessoa: [''],
    id: [''],
    curso: ['', Validators.required],
    instituicao: ['', Validators.required],
    anoInicio: ['', Validators.required],
    anoFim: [''],
    situacao: ['', Validators.required],
    semestre: [''],
    nroRegConselho: [''],
  })


  ngOnInit(): void {
  }

  buscaCurso(event) {
    this.tiposDeQualificaoService.getResultCurso(event.query).subscribe((data) => {
      this.listaTiposDeQualificao = data.content;
    });
  }

  buscaInstituicao(event) {
    this.instituicaoService.getResultInstituicao(event.query).subscribe((data) => {
      this.listaInstituicao = data.content;
    });
  }

  buscaServidor(event) {
    this.servidorService.getResultServidor(event.query).subscribe((data) => {
      this.listaServidor = data.content;
    });
  }

  submit() {
    const grad = this.formGraduacao.value;
    const graduacao: GraduacaoModel = {
      id: grad.id,
      curso: grad.curso?.descricao,
      instituicao: grad.instituicao?.descricao,
      anoInicio: grad.anoInicio,
      anoFim: grad.anoFim,
      situacao: grad.situacao,
      semestre: grad.semestre,
      nroRegConselho: grad.nroRegConselho,
      servidorId: grad.servidor?.id,
      instituicaoId: grad.instituicao?.id,
      tiposDeQualificacaoId: grad.curso?.id,
    };

    if (this.formGraduacao.value.id != 0) {
      this.graduacaoService.alterarServidor(graduacao).subscribe((e) => {
        this.formGraduacao.reset();
      });
    } else {
      this.graduacaoService.cadastrarServidor(graduacao).subscribe((e) => {
        console.log(graduacao.servidorId)
        this.formGraduacao.reset();
      });
    }
  }

  valid(): boolean {
    return this.formGraduacao.valid;
  }
}
