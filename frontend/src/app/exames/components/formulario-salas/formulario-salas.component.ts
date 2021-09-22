import { UnidadeFuncionalComponent } from './../../../shared/components/unidade-funcional/unidade-funcional.component';
import { Component, OnInit, ViewChild } from '@angular/core';
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
  
  @ViewChild(UnidadeFuncionalComponent)
  unidadeFuncional: UnidadeFuncionalComponent;

  constructor(
    private salaService: SalasService){ }


    situacaoExame = SituacaoAtivo;

    cadastrar(){

      let cadastro: Sala = {
        codigoDaSala: this.codigoDaSala,
        identificacaoDaSala: this.identificacaoDaSala,
        localizacaoDaSala: this.localizacaoDaSala,
        ativo: this.ativo,
        unidadeExecutoraId: this.unidadeFuncional.unidadeId,
      };
      
      this.unidadeFuncional.imprimirId();

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
