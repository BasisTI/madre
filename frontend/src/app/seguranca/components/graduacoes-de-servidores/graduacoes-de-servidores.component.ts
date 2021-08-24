import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatatableComponent } from '@nuvem/primeng-components';
import { ElasticQuery } from '@shared/elastic-query';
import { ListaServidor } from '../../models/dropdowns/lista-servidor';
import { Pessoa } from '../../models/pessoa-model';
import { Servidor } from '../../models/servidor-resumo-model';
import { Vinculo } from '../../models/vinculo-resumo-model';
import { ServidorService } from '../../services/servidor.service';

@Component({
  selector: 'app-graduacoes-de-servidores',
  templateUrl: './graduacoes-de-servidores.component.html',
  styleUrls: ['./graduacoes-de-servidores.component.css']
})
export class GraduacoesDeServidoresComponent implements OnInit {

  elasticQuery: ElasticQuery = new ElasticQuery();
    
  @ViewChild(DatatableComponent) datatable: DatatableComponent;

  listaServidor = new Array<ListaServidor>();

  graduacao: FormGroup = this.fb.group({
    matricula: [''],
    vinculo: [''],
    nome: [''],
  });

  constructor(
    private fb: FormBuilder,
    private servidorService: ServidorService,
  ) { }

  ngOnInit(): void {
  }

  searchUrl:string = 'seguranca/api/_search/servidors';

  rowsPerPageOptions: number[] = [5,10,20]

  buscaServidor(event) {
    this.servidorService.getResultServidor(event.query).subscribe((data) => {
      this.listaServidor = data.content;
    });
  }

  pesquisar(){
    this.datatable.refresh(this.elasticQuery.query);
  }

  ngAfterViewInit(): void {
    this.pesquisar();
  } 

  aoSelecionarUmaMatricula(servidor: Pessoa, vinculo: Vinculo): void {
    this.graduacao.controls['vinculoId'].setValue(vinculo.id);
    this.graduacao.controls['nome'].setValue(servidor.nome);
  }

}
