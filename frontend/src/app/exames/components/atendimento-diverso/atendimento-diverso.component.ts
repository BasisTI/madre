import { CadaverModel } from './../../models/subjects/cadaver-model';
import { Component, OnInit } from '@angular/core';
import { CadaverService } from '../../services/cadaver.service';
import { group } from '@angular/animations';

@Component({
  selector: 'app-atendimento-diverso',
  templateUrl: './atendimento-diverso.component.html',
  styleUrls: ['./atendimento-diverso.component.css'],

})
export class AtendimentoDiversoComponent implements OnInit {

  groups: CadaverModel[];

  TipoAmostra: string[] = ["Selecione","Doador","Receptor",];
  selectedTipo: string; 

  OrigemAmostra: string[] = ["Selecione","Humano","Não Humano",];
  selectedOrigem: string;

  Sexo: string[] = ["Selecione","Feminino","Masculino","Ignorado",];
  selectedSexo: string;

  teste: string[] = ["Selecione","teste","teste01",];
  selectedTeste: string;
  
  public exames = new Array<CadaverModel>();

  constructor(private CadaverService: CadaverService) { }
  


     ngOnInit() : void{
       this.CadaverService.GetCadaver().subscribe((response)=>{
         this.groups = response;
       }

       )
     }
    
}
