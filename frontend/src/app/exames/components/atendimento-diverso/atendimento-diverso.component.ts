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
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { PacientesService } from '../../services/paciente.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AtendimentoDiversoService } from '../../services/atendimentodiverso.service';
import { TipoAmostraDropdown } from "../../models/dropdowns/tipoAmostra.dropdown";
import { OrigemAmostraDropdown } from "../../models/dropdowns/origemAmostra.dropdown";
import { SexoDropdown } from "../../models/dropdowns/sexo.dropdown";
import { UnidadeFuncional } from "../../models/subjects/unidade-model";

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

  tipoAmostraDropdown = TipoAmostraDropdown;
  origemAmostraDropdown = OrigemAmostraDropdown;
  sexoDropdown = SexoDropdown;

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

        unidadeExecutoraId : [null],

        origemAmostra : [null],

        tipoAmostra : [null],

        identificacao : [null],

        dataSoro : [null],

        material : [null],

        especialidadeId : [null],

        centroAtividadeId : [null],
        
        dataNascimento : [null],

        sexo : [null],

        laboratorioId:[null],

        controleId:[null],

        cadaverId:[null]

    });

       this.cadaverService.getCadaver().subscribe((response)=>{
         this.cadavers = response; 
       });

       this.controleQualidadeservice.getControleQualidade().subscribe((response)=>{
        this.controles = response; 
       });

       this.laboratorioExternoService.getLaboratorioExterno().subscribe((response)=>{
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
       this.unidadeFuncionalService.getUnidades().subscribe((response)=>{
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

