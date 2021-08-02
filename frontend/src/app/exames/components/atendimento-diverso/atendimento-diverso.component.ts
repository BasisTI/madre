import { PageNotificationService } from '@nuvem/primeng-components';
import { PreCadastroModel } from './../../../pacientes/models/pre-cadastro-model';
import { EspecialidadeService } from '@internacao/services/especialidade.service';
import { Especialidade } from './../../../internacao/models/especialidade';
import { CadaverModel } from './../../models/subjects/cadaver-model';
import { Component, OnInit } from '@angular/core';
import { CadaverService } from '../../services/cadaver.service';
import { ControleQualidadeservice } from '../../services/controleQualidade.service';
import { ControleQualidadeModel } from '../../models/subjects/controleQualidade-model';
import { LaboratorioExternoService } from '../../services/laboratorioExterno.service';
import { LaboratorioExternoModel } from '../../models/subjects/laboratorioExterno-model';
import { CentroService } from '@internacao/formulario-unidades/services/centro-de-atividade.service';
import { CentroDeAtividade } from '@internacao/formulario-unidades/models/dropwdowns/centro-de-atividade';
import { UnidadeFuncional } from '../../models/subjects/unidade-funcional-model';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { PacientesService } from '../../services/paciente.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AtendimentoDiversoService } from '../../services/atendimentodiverso.service';


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
  especialidades: Especialidade[];
  unidadesFuncionais: UnidadeFuncional[];
  cadastros: PreCadastroModel[];

  TipoAmostra: string[] = ["Doador","Receptor"];
  selectedTipo: string; 

  OrigemAmostra: string[] = ["Humano","Não Humano"];
  selectedOrigem: string;

  Sexo: string[] = ["Feminino","Masculino","Ignorado"];
  selectedSexo: string;

  valor: number;

  cadastroAtendimentoDiverso :FormGroup

  constructor(
    private fb: FormBuilder,
    private cadaverService: CadaverService, 
    private controleQualidadeservice: ControleQualidadeservice,
    private laboratorioExternoService: LaboratorioExternoService,
    private centroService: CentroService,
    private especialidadeservice: EspecialidadeService,
    private unidadeFuncionalService: UnidadeFuncionalService,
    private pacientesService: PacientesService,
    private atendimentoDiversoService: AtendimentoDiversoService,
    private pageNotificationService: PageNotificationService
    ) { }

     ngOnInit() : void{
      this.cadastroAtendimentoDiverso = this.fb.group({
        codigo: [null, Validators.required],
        informacoesId:[null],
        projetoId: [null],
        laboratorioId: [null],
        controleId:[null],
        cadaverId:[null]
    });

       this.cadaverService.GetCadaver().subscribe((response)=>{
         this.cadavers = response; 
       });

       this.controleQualidadeservice.GetControleQualidade().subscribe((response)=>{
        this.controles = response; 
       });

       this.laboratorioExternoService.GetLaboratorioExterno().subscribe((response)=>{
        this.laboratorios = response; 
       });

       this.centroService.getListaDeCentros().subscribe((response)=>{
         this.centros = response;
       });

       this.pacientesService.getPaciente().subscribe((response)=>{
         this.cadastros = response;
       });

       this.especialidadeservice.getEspecialidades().subscribe((response)=>{
        this.especialidades = response; 
       });
       this.unidadeFuncionalService.getUnidadeFuncional().subscribe((response)=>{
       this.unidadesFuncionais = response; 
      });
  }

  valid(): boolean {
    return this.cadastroAtendimentoDiverso.valid;
  }

    cadastrar() { 
      if (!this.cadastroAtendimentoDiverso.valid){
        this.pageNotificationService.addErrorMessage('preencher o campo codigo')
        return;
      }
      let atendimentoDiverso = this.cadastroAtendimentoDiverso.value;

    this.atendimentoDiversoService.cadastrarAtendimento(atendimentoDiverso).subscribe();
}
}

