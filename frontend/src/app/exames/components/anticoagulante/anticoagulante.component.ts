import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AnticoagulanteService } from '../../services/anticoagulante.service';
import { Anticoagulante } from '../../models/subjects/anticoagulante';
import { fbind } from 'q';
import { SituacaoExame } from '../../models/dropdowns/situacao.dropdown';

@Component({
  selector: 'app-anticoagulante',
  templateUrl: './anticoagulante.component.html',
  styleUrls: ['./anticoagulante.component.css']
})
export class AntiCoagulanteComponent implements OnInit {

  nome: string;
  ativo: boolean;
  
  constructor(
    private fb: FormBuilder,
    private anticoagulanteService: AnticoagulanteService) { }
    
    situacaoExame = SituacaoExame;
    
    ngOnInit(): void {}
    
    cadastrar(){

      let cadastro: Anticoagulante = {
        nome: this.nome,
        ativo: this.ativo
      };

      
      this.anticoagulanteService.cadastrarAnticoagulante(cadastro).subscribe();
      this.limparFormulario();
    }
    
    limparFormulario() {
      this.nome = null;
      this.ativo = null;
    }
    
    validarFormulario() {
      if(this.nome && this.ativo)
      return true;
    }
    
  }

