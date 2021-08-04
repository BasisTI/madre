import { Component, OnInit } from '@angular/core';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { CentroAtividadeService } from '@suprimentos/services/centro-atividade.service';
import { Ocupacao } from 'src/app/pacientes/models/dropdowns/types/ocupacao';
import { Pessoa } from '../../models/pessoa-model';
import { Vinculo } from '../../models/vinculo-model';
import { PessoaService } from '../../services/pessoa.service';
import { VinculoService } from '../../services/vinculo.service';
import { OcupacaoService } from 'src/app/pacientes/formulario/dados-pessoais/ocupacao.service';
import { TIPO_DE_REMUNERACAO } from '../../models/dropdowns/tipo-de-remuneracao';
import { Router } from '@angular/router';
import { ViewChild } from '@angular/core';
import { DatatableClickEvent, DatatableComponent } from '@nuvem/primeng-components';
import { ElasticQuery } from '@shared/elastic-query';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
@Component({
  selector: 'app-servidores',
  templateUrl: './servidores.component.html',
  styleUrls: ['./servidores.component.css']
})
export class ServidoresComponent implements OnInit {

  pessoas: Pessoa[] = [];
  vinculos: Vinculo[] = [];
  centros: CentroAtividade[];
  ocupacao: Ocupacao[];

  constructor(private pessoaService: PessoaService, private vinculoService: VinculoService, private centrosService: CentroAtividadeService,
    private ocupacaoService: OcupacaoService, private router: Router) { }

  situacaoDoServidor = OPCOES_DE_SITUACOES;
  tipoDeRemuneracao = TIPO_DE_REMUNERACAO;
  elasticQuery: ElasticQuery = new ElasticQuery();
  @ViewChild(DatatableComponent) datatable: DatatableComponent;

  searchUrl: string = 'seguranca/api/_search/servidors';
  rowsPerPageOptions: number[] = [5, 10, 20];

  ngOnInit(): void {
    this.pessoaService.getPessoa().subscribe((response) => { this.pessoas = response; });
    this.vinculoService.getVinculo().subscribe((response) => { this.vinculos = response; });
    this.centrosService.getCentros().subscribe((response) => { this.centros = response; });
    this.ocupacaoService.getOcupacoes().subscribe((response) => { this.ocupacao = response; });
  }

  pesquisar() {
    this.datatable.refresh(this.elasticQuery.query);
  }

  // onClick(event: DatatableClickEvent) {
  //   switch (event.button) {
  //     case 'edit': {
  //       this.abrirEditar(event.selection);
  //       break;
  //     }
  //     case 'view': {
  //       this.abrirVisualizar(event.selection);
  //       break;
  //     }
  //     case 'delete': {
  //       this.confirmarDelete(event.selection);
  //       break;
  //     }
  //     default: {
  //       break;
  //     }
  //   }
  // }

}
