import { Component, OnInit } from '@angular/core';
import { OrigemDaInternacao } from '@internacao/models/origem-da-internacao';
import { OrigemDaInternacaoService } from '@internacao/services/origem-da-internacao.service';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { OPCOES_DE_ORIGEM } from '../../models/dropdowns/opcoes-de-origem';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';

@Component({
  selector: 'app-pesquisar-atendimento',
  templateUrl: './pesquisar-atendimento.component.html',
  styleUrls: ['./pesquisar-atendimento.component.css']
})
export class PesquisarAtendimentoComponent implements OnInit {

  constructor(private unidadeFuncionalService: UnidadeFuncionalService, 
              private origemdDaInternacaoService: OrigemDaInternacaoService) { }


  unidades: UnidadeFuncional[] = [];
  origens: OrigemDaInternacao[] = [];

  ngOnInit(): void {
    this.unidadeFuncionalService.GetUnidades().subscribe((response) => {
      this.unidades = response;
    });

    this.origemdDaInternacaoService.getOrigensDaInternacao().subscribe((response) => {
      this.origens = response;
    });

  }

}
