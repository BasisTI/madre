import { Component, OnInit, ViewChild } from '@angular/core';
import { DatatableComponent } from '@nuvem/primeng-components';
import { ElasticQuery } from '@shared/elastic-query';
import { ListaServidor } from '../../../models/dropdowns/lista-servidor';
import { ServidorService } from '../../../services/servidor.service';

@Component({
  selector: 'app-graduacoes-de-servidores',
  templateUrl: './graduacoes-de-servidores.component.html',
  styleUrls: ['./graduacoes-de-servidores.component.css']
})
export class GraduacoesDeServidoresComponent implements OnInit {

  elasticQuery: ElasticQuery = new ElasticQuery();

  @ViewChild(DatatableComponent) datatable: DatatableComponent;

  searchUrl:string = 'seguranca/api/_search/graduacoes';

  rowsPerPageOptions: number[] = [5,10,20];

  listaServidor = new Array<ListaServidor>();
  
  constructor(
    private servidorService: ServidorService,
  ) { }

  ngOnInit(): void {
  }

  buscaServidor(event) {
    this.servidorService.getResultServidor(event.query).subscribe((data) => {
      this.listaServidor = data.content;
    });
  }

  pesquisar(){
    this.datatable.refresh(this.elasticQuery.query);
  }

  limparPesquisa() {
    this.elasticQuery.reset();
    this.datatable.refresh(this.elasticQuery.reset)
  }
}
