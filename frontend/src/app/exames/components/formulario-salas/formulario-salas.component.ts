import { Component, OnInit, ModuleWithProviders } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SalasService } from '../../services/salas.service';
import { Sala } from '../../models/subjects/sala';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';

@Component({
  selector: 'app-formulario-salas',
  templateUrl: './formulario-salas.component.html',
  styleUrls: ['./formulario-salas.component.css']
})
export class FormularioSalasComponent implements OnInit {

  codigoDaSala: number;
  identificacaoDaSala: string;
  localizacaoDaSala: string;
  ativo: boolean; 

  constructor(
    private fb: FormBuilder,
    private salaService: SalasService){ }


    situacaoExame = SituacaoAtivo;

    cadastrar(){

      let cadastro: Sala = {
        codigoDaSala: this.codigoDaSala,
        identificacaoDaSala: this.identificacaoDaSala,
        localizacaoDaSala: this.localizacaoDaSala,
        ativo: this.ativo
      };


      this.salaService.cadastrarSala(cadastro).subscribe();
      this.limparSala();
    }

    limparSala() {
      this.codigoDaSala = null;
      this.identificacaoDaSala = null;
      this.localizacaoDaSala = null;
      this.ativo = null;
    }

    validarSala() {
      if(this.codigoDaSala && this.identificacaoDaSala && this.localizacaoDaSala && this.ativo)
      return true;
  }

  ngOnInit(): void {}

}
