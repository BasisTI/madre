import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CLASSIFICACAO_RISCO } from 'src/app/pacientes/models/radioButton/classificacao-risco';

@Component({
  selector: 'app-nivel-emergencia',
  templateUrl: './nivel-emergencia.component.html',
  styleUrls: ['./nivel-emergencia.component.css']
})
export class NivelEmergenciaComponent implements OnInit {

  @Input() formTriagem: FormGroup;

  @Input()
  title: string;

  @Input()
  descricao: string;

  @Input()
  cor: string;

  @Output()
  value: string;
  
  opcaoClassificacao = CLASSIFICACAO_RISCO;

  constructor() { }

  ngOnInit(): void {
  }

  classificacaoDeRisco() {
    console.log("skdjalsifjikp");
  }

  valor(valor:number){
    console.log(valor);
    this.value = this.opcaoClassificacao[valor].value
  }

}
