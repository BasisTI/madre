import { FormBuilder, Validators } from '@angular/forms';
import { Anticoagulante } from './../../models/subjects/anticoagulante';
import { SituacaoExame } from './../../models/dropdowns/situacao.dropdown';
import { RecipienteI } from './../../models/subjects/recipiente';
import { CadastrarRecepienteService } from './../../services/cadastrar-recipientes.service';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-cadastrar-recipientes',
  templateUrl: './cadastrar-recipientes.component.html',
  styleUrls: ['./cadastrar-recipientes.component.css']
})
export class CadastrarRecipientesComponent implements OnInit {

    nome: string;
    anticoagulante: Anticoagulante;
    ativo: boolean;

  constructor(private fb: FormBuilder,
              private recipienteService: CadastrarRecepienteService) { }

    situacao = SituacaoExame;

    cadastrarRecipiente = this.fb.group({
        nome: [null, Validators.required],
        anticoagulante: [null, Validators.required],
        ativo: [null, Validators.required]
    });

    cadastrar(){
      let cadastroRecipiente = this.cadastrarRecipiente.value;

      let cadastro: RecipienteI = {
          nome: cadastroRecipiente.nome,
          anticoagulante: cadastroRecipiente.anticoagulante,
          ativo: cadastroRecipiente.ativo
      };

      console.log(cadastroRecipiente);

      this.recipienteService.cadastrarRecipiente(cadastro).subscribe();
      this.limparResipiente();
      }

    limparResipiente(){
        this.cadastrarRecipiente.reset();
    }

    valid(){
        if(this.cadastrarRecipiente.valid)
            return true;
        else
            return false;
    }

  ngOnInit(): void {}

}