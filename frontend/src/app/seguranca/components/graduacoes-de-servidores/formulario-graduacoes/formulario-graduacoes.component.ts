import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ListaInstituicao } from 'src/app/seguranca/models/dropdowns/lista-instituicao';
import { ListaTiposDeQualificacao } from 'src/app/seguranca/models/dropdowns/lista-tipo-de-qualificacao';
import { OPCOES_DE_SITUACOES_GRADUACAO } from 'src/app/seguranca/models/dropdowns/opcoes-de-situacoes-graduacao';
import { GraduacaoModel } from 'src/app/seguranca/models/graduacao-model';
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
  ) { }

  listaTiposDeQualificao = new Array<ListaTiposDeQualificacao>();
  listaInstituicao = new Array<ListaInstituicao>();
  situacaoDaGraduacao = OPCOES_DE_SITUACOES_GRADUACAO;

  formGraduacao = this.fb.group({
    curso: [''],
    instituicao: [''],
    anoInicio: [''],
    anoFim: [''],
    situacaoDaGraduacao: [''],
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

  submit() {
    const grad = this.formGraduacao.value;
    const graduacao: GraduacaoModel = {
      curso: grad.curso,
      instituicao: grad.instituicao,
      anoInicio: grad.anoInicio,
      anoFim: grad.anoFim,
      situacaoDaGraduacao: grad.situacaoDaGraduacao,
      semestre: grad.semestre,
      nroRegConselho: grad.nroRegConselho,
    };
  }
}
