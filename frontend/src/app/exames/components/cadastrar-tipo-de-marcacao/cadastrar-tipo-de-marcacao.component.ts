import { TipoDeMarcacao } from './../../models/subjects/tipo-de-marcacao';
import { CadastrarTiposDeMarcacaoService } from './../../services/cadastrar-tipos-de-marcacao.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cadastrar-tipo-de-marcacao',
  templateUrl: './cadastrar-tipo-de-marcacao.component.html',
  styleUrls: ['./cadastrar-tipo-de-marcacao.component.css']
})
export class CadastrarTipoDeMarcacaoComponent implements OnInit {

    tipoDeMarcacaoNome: string;
    ativo: boolean;

  constructor(private fb: FormBuilder,
              private tipoMarcacaoService: CadastrarTiposDeMarcacaoService) { }


   cadastrarTipoMarcacao = this.fb.group({
       tipoDeMarcacaoNome: [null, Validators.required],
       ativo: [null, Validators.required]
   });

   ngOnInit(): void {
    }

   cadastrar(){
        let cadastroTipoMarcacao = this.cadastrarTipoMarcacao.value;

        let cadastro: TipoDeMarcacao = {
            tipoDeMarcacaoNome: cadastroTipoMarcacao.tipoDeMarcacaoNome,
            ativo: cadastroTipoMarcacao.ativo
        };

        console.log(cadastroTipoMarcacao);

        this.tipoMarcacaoService.cadastrarTipoMarcacao(cadastro).subscribe();
        this.limparTipoDeMarcacao();
   }

   validTipodDeMarcacao(){
       if(this.cadastrarTipoMarcacao.valid)
            return true;
        else
            return false;
   }

   limparTipoDeMarcacao(){
        this.cadastrarTipoMarcacao.reset();
   }


}
