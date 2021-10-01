import { ResponsavelDropdown } from './../../models/dropdowns/responsavel.dropdown';
import { RecomendacoesExames } from './../../models/subjects/grupos-de-recomendacoes-de-exames';
import { GruposDeRecomendacoesDeExamesService } from './../../services/grupos-de-recomendacoes-de-exames.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';



@Component({
  selector: 'app-grupos-de-recomendacoes-de-exames',
  templateUrl: './grupos-de-recomendacoes-de-exames.component.html',
  styleUrls: ['./grupos-de-recomendacoes-de-exames.component.css']
})
export class GruposDeRecomendacoesDeExamesComponent implements OnInit {
  descricaoId: string;
  responsavelId: number;
  abrangenciaId: number;
  ativo: boolean;

  responsaveis = ResponsavelDropdown;
  
  constructor(private fb: FormBuilder,
    private gruposDeRecomendacoesDeExamesService: GruposDeRecomendacoesDeExamesService) { }
  
  cadastrar(){
    let cadastro: RecomendacoesExames = {
      descricaoId: this.descricaoId,
      responsavelId: this.responsavelId,
      abrangenciaId: this.abrangenciaId,
      ativo: this.ativo,
    };
    
    this.gruposDeRecomendacoesDeExamesService.cadastrarGrupos(cadastro).subscribe();
    this.limparFormulario();
  }

  limparFormulario(){
    this.descricaoId = null;
    this.responsavelId = null;
    this.abrangenciaId = null;
    this.ativo = null;
  }

  validarFormulario() {
    if(this.descricaoId && this.responsavelId && this.abrangenciaId && this.ativo)
    return true;
  }
    
  ngOnInit(): void {
  }

}
