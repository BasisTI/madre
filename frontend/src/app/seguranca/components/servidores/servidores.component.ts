import { Component, OnInit } from '@angular/core';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { CentroAtividadeService } from '@suprimentos/services/centro-atividade.service';
import { PessoaService } from '../../services/pessoa.service';
import { VinculoService } from '../../services/vinculo.service';
import { TIPO_DE_REMUNERACAO } from '../../models/dropdowns/tipo-de-remuneracao';
import { ViewChild } from '@angular/core';
import { DatatableComponent } from '@nuvem/primeng-components';
import { ElasticQuery } from '@shared/elastic-query';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { Pessoa } from '../../models/pessoa-model';
import { Vinculo } from '../../models/vinculo-resumo-model';
import { OcupacaoDeCargo } from '../../models/dropdowns/ocupacao-de-cargo';
import { OcupacoesDeCargoService } from '../../services/ocupacoes-de-cargos.service';
@Component({
  selector: 'app-servidores',
  templateUrl: './servidores.component.html',
  styleUrls: ['./servidores.component.css']
})
export class ServidoresComponent implements OnInit {

  pessoas: Pessoa[] = [];
  vinculos: Vinculo[] = [];
  centros: CentroAtividade[];
  ocupacaoDeCargos: OcupacaoDeCargo[];

  constructor(private pessoaService: PessoaService, private vinculoService: VinculoService, private centrosService: CentroAtividadeService,
    private ocupacaoDeCargosService: OcupacoesDeCargoService) { }

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
    this.ocupacaoDeCargosService.getOcupacoesDeCargo().subscribe((response) => { this.ocupacaoDeCargos = response; });
  }

  pesquisar() {
    this.datatable.refresh(this.elasticQuery.query);
  }
}
