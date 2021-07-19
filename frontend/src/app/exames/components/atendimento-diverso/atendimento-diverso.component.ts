import { ControleQualidade } from './../../models/controleQualidade';
import { CadaverModel } from './../../models/subjects/cadaver-model';
import { Component, OnInit } from '@angular/core';
import { CadaverService } from '../../services/cadaver.service';
import { group } from '@angular/animations';
import { ControleQualidadeservice } from '../../services/controleQualidade.service';
import { ControleQualidadeModel } from '../../models/subjects/controleQualidade-model';
import { LaboratorioExternoService } from '../../services/laboratorioExterno.service';
import { LaboratorioExternoModel } from '../../models/subjects/laboratorioExterno-model';
import { CentroService } from '@internacao/formulario-unidades/services/centro-de-atividade.service';
import { CentroDeAtividade } from '@internacao/formulario-unidades/models/dropwdowns/centro-de-atividade';



@Component({
  selector: 'app-atendimento-diverso',
  templateUrl: './atendimento-diverso.component.html',
  styleUrls: ['./atendimento-diverso.component.css'],

})
export class AtendimentoDiversoComponent implements OnInit {

  cadavers: CadaverModel[];
  controles: ControleQualidadeModel[];
  laboratorios: LaboratorioExternoModel[];
  centros: CentroDeAtividade[];

  TipoAmostra: string[] = ["Selecione","Doador","Receptor",];
  selectedTipo: string; 

  OrigemAmostra: string[] = ["Selecione","Humano","Não Humano",];
  selectedOrigem: string;

  Sexo: string[] = ["Selecione","Feminino","Masculino","Ignorado",];
  selectedSexo: string;

  teste: string[] = ["Selecione","teste","teste01",];
  selectedTeste: string;
  
  public cadaver = new Array<CadaverModel>();
  public controle = new Array<ControleQualidadeModel>();
  public laboratorio = new Array<LaboratorioExternoModel>();

  constructor(private CadaverService: CadaverService, 
    private ControleQualidadeservice: ControleQualidadeservice,
     private LaboratorioExternoService: LaboratorioExternoService,
     private centroService: CentroService) { }

     ngOnInit() : void{
       this.CadaverService.GetCadaver().subscribe((response)=>{
         this.cadavers = response; 
       });

       this.ControleQualidadeservice.GetControleQualidade().subscribe((response)=>{
        this.controles = response; 
       });

       this.LaboratorioExternoService.GetLaboratorioExterno().subscribe((response)=>{
        this.laboratorios = response; 
       });

       this.LaboratorioExternoService.GetLaboratorioExterno().subscribe((response)=>{
        this.laboratorios = response; 
       });
       this.centroService.getListaDeCentros().subscribe((response)=>{
         this.centros = response;
       })
     }
    
}
