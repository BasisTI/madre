import { UnidadeFuncionalComponent } from './../../../shared/components/unidade-funcional/unidade-funcional.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { SalasService } from '../../services/salas.service';
import { Sala } from '../../models/subjects/sala';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-formulario-salas',
  templateUrl: './formulario-salas.component.html',
  styleUrls: ['./formulario-salas.component.css']
})
export class FormularioSalasComponent implements OnInit {

  codigoDaSala: number;
  identificacaoDaSala: string;
  locacaoDaSala: string;
  ativo: boolean;

  situacaoExame = SituacaoAtivo;
  
  @ViewChild(UnidadeFuncionalComponent)
  unidadeFuncional: UnidadeFuncionalComponent;
  
  constructor(private salaService: SalasService, private msg: MessageService){ }
    
    ngOnInit(): void {}


    cadastrar(){

      let cadastro: Sala = {
        codigoDaSala: this.codigoDaSala,
        identificacaoDaSala: this.identificacaoDaSala,
        locacaoDaSala: this.locacaoDaSala,
        ativo: this.ativo,
        unidadeExecutoraId: this.unidadeFuncional.unidadeId,
      };
      
      this.unidadeFuncional.imprimirId();

      if(this.isCodigoDaSalaZeroOuNegativo(this.codigoDaSala)){
        return;
      }else {
        this.salaService.cadastrarSala(cadastro).subscribe();
        this.limparFormulario();
      }

    }

    limparFormulario() {
      this.codigoDaSala = null;
      this.identificacaoDaSala = null;
      this.locacaoDaSala = null;
      this.ativo = null;
      this.unidadeFuncional.unidadeId = null
      
    }

    validarFormulario() {
      if(this.codigoDaSala && this.identificacaoDaSala && this.locacaoDaSala && this.ativo)
      return true;
    }

    isCodigoDaSalaZeroOuNegativo(codigoDaSala: number): boolean {
      if(codigoDaSala <= 0) {
        this.msg.add({
          severity: 'error', summary: 'Erro no preenchimento',
          detail: 'Código da sala deve ser maior que zero'
        });
        return true;
      }
    }


}
