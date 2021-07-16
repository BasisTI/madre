import { Component, OnInit } from '@angular/core';
import { OPCOES_DE_ORIGEM } from '../../models/dropdowns/opcoes-de-origem';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';

@Component({
  selector: 'app-pesquisar-atendimento',
  templateUrl: './pesquisar-atendimento.component.html',
  styleUrls: ['./pesquisar-atendimento.component.css']
})
export class PesquisarAtendimentoComponent implements OnInit {

  constructor(private unidadeFuncionalService: UnidadeFuncionalService) { }

  opcoesDeOrigem = OPCOES_DE_ORIGEM;
  unidades: UnidadeFuncional[] = [];

  ngOnInit(): void {
    this.unidadeFuncionalService.GetGrupos().subscribe((response) => {
      this.unidades = response;
      console.log(response);
      
    });
  }

}
