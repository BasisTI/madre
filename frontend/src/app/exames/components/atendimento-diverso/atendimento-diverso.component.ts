import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-atendimento-diverso',
  templateUrl: './atendimento-diverso.component.html',
  styleUrls: ['./atendimento-diverso.component.css']
})
export class AtendimentoDiversoComponent implements OnInit {

  

  
  TipoAmostra: string[] = ["Selecione","Doador","Receptor",];
  selectedTipo: string; 

  OrigemAmostra: string[] = ["Selecione","Humano","Não Humano",];
  selectedOrigem: string;

  Sexo: string[] = ["Selecione","Feminino","Masculino","Ignorado",];
  selectedSexo: string;

  teste: string[] = ["Selecione","teste","teste01",];
  selectedTeste: string;

  constructor() { }
  seila: string;
  ngOnInit(): void {
  }
  pesquisar() {
    console.log(this.seila);
  }

}
