import { Component, OnInit } from '@angular/core';
import { OPCOES_DE_ORIGEM } from '../../models/dropdowns/opcoes-de-origem';
import { UnidadeFuncional } from '../../models/subjects/unidade-funcional-model';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';

@Component({
  selector: 'app-pesquisar-atendimento',
  templateUrl: './pesquisar-atendimento.component.html',
  styleUrls: ['./pesquisar-atendimento.component.css']
})
export class PesquisarAtendimentoComponent implements OnInit {

  opcoesDeOrigem = OPCOES_DE_ORIGEM;
  unidadesFuncionais: UnidadeFuncional[] = [];

  constructor(private unidadeFuncionalService: UnidadeFuncionalService) { }



  ngOnInit(): void {
    this.unidadeFuncionalService.getUnidadeFuncional().subscribe((response) => {
      this.unidadesFuncionais = response;

    });
  }

}
