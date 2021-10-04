import { ResponsavelDropdown } from './../../models/dropdowns/responsavel.dropdown';
import { RecomendacoesExames } from './../../models/subjects/grupos-de-recomendacoes-de-exames';
import { GruposDeRecomendacoesDeExamesService } from './../../services/grupos-de-recomendacoes-de-exames.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AbrangeciaDropdown } from '../../models/dropdowns/abrangencia.dropdown';



@Component({
  selector: 'app-grupos-de-recomendacoes-de-exames',
  templateUrl: './grupos-de-recomendacoes-de-exames.component.html',
  styleUrls: ['./grupos-de-recomendacoes-de-exames.component.css']
})
export class GruposDeRecomendacoesDeExamesComponent implements OnInit {
  descricao: string;
  responsavel: string;
  abrangencia: string;
  ativo: boolean;

  responsaveis = ResponsavelDropdown;
  abrangencia1 = AbrangeciaDropdown;
  
  constructor(private fb: FormBuilder,
    private gruposDeRecomendacoesDeExamesService: GruposDeRecomendacoesDeExamesService) { }
  
  cadastrar(){
    let cadastro: RecomendacoesExames = {
      descricao: this.descricao,
      responsavel: this.responsavel,
      abrangencia: this.abrangencia,
      ativo: this.ativo,
    };
    
    this.gruposDeRecomendacoesDeExamesService.cadastrarGrupos(cadastro).subscribe();
    this.limparFormulario();
  }

  limparFormulario(){
    this.descricao = null;
    this.responsavel = null;
    this.abrangencia = null;
    this.ativo = null;
  }

  validarFormulario() {
    if(this.descricao && this.responsavel && this.abrangencia && this.ativo)
    return true;
  }
    
  ngOnInit(): void {
  }

}
