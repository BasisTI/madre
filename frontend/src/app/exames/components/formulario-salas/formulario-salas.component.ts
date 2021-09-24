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

  identificacaoDaSala: string;
  locacaoDaSala: string;
  ativo: boolean;

  situacaoExame = SituacaoAtivo;
  
  @ViewChild(UnidadeFuncionalComponent)
  unidadeFuncional: UnidadeFuncionalComponent;
  
  constructor(private salaService: SalasService){ }
    
    ngOnInit(): void {}


    cadastrar(){

      let cadastro: Sala = {
        identificacaoDaSala: this.identificacaoDaSala,
        locacaoDaSala: this.locacaoDaSala,
        ativo: this.ativo,
        unidadeExecutoraId: this.unidadeFuncional.unidadeId,
      };
      
      this.unidadeFuncional.imprimirId();

      this.salaService.cadastrarSala(cadastro).subscribe();
      this.limparFormulario();
    }

    limparFormulario() {
      this.identificacaoDaSala = null;
      this.locacaoDaSala = null;
      this.ativo = null;
      this.unidadeFuncional.unidadeId = null
      
    }

    validarFormulario() {
      if(this.identificacaoDaSala && this.locacaoDaSala && this.ativo)
      return true;
    }


}
