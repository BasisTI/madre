import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';
import { GradeDeAgendamentoService } from '../../services/grade-de-agendamento.service';
import { GrupoModel } from '../../models/subjects/grupo-model';
import { ExamModel } from '../../models/subjects/exames-model';
import { GradesDeAgendamento } from '../../models/subjects/grades-de-agendamento';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { Sala } from '../../models/subjects/sala';
import { ExamesService } from '../../services/exames.service';
import { GruposExamesService } from '../../services/grupos-exames.service';
import { Servidor } from 'src/app/seguranca/models/dropdowns/servidor-model';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';

@Component({
  selector: 'app-formulario-grade-de-agendamento',
  templateUrl: './formulario-grade-de-agendamento.component.html',
  styleUrls: ['./formulario-grade-de-agendamento.component.css']
})
export class FormularioGradeDeAgendamentoComponent implements OnInit {

  unidadesExecutoras: UnidadeFuncional[] = [];
  servidores: Servidor[] = [];
  gruposDeExame: GrupoModel[] = [];
  salas: Sala[] = [];
  exames: ExamModel[] = [];
  
  exameSelecionado: number;
  salaSelecionada: number;
  grupoSelecionado: number;
  gradeId: number;
  
  situacaoGrade = SituacaoAtivo;

  @Input()
  grade: GradesDeAgendamento;

  @Output()
  gradeSalva = new EventEmitter<GradesDeAgendamento>();
  
  constructor(private fb: FormBuilder,
              private gradeAgendamentoService: GradeDeAgendamentoService,
              private unidadeFuncionalService: UnidadeFuncionalService,
              private servidorService: ServidorService,
              private exameService: ExamesService,
              private grupoExameService: GruposExamesService) { }

  cadastroGrade = this.fb.group({
    unidadeExecutoraId: [null, Validators.required],
    responsavelId: [null, Validators.required],
    ativo: [null, Validators.required],
    exameGradeId: [null],
    grupoGradeId: [null],
    salaGradeId: [null, Validators.required],
  });

  validarFormulario(): boolean {
    if (this.cadastroGrade.valid && (this.exameSelecionado || this.cadastroGrade
        .get('grupoGradeId').value != null)){
          return true; 
        } else {
          return false
        }
  }
  
  limparFormulario() {
    this.cadastroGrade.reset();    
  }
  
  cadastrarGradeDeAgendamento(){
    const cadastroGradeValor = this.cadastroGrade.value;

    this.grade = {
      unidadeExecutoraId: cadastroGradeValor.unidadeExecutoraId,
      responsavelId: cadastroGradeValor.responsavelId,
      ativo: cadastroGradeValor.ativo,
      salaGradeId: this.salaSelecionada,
      salaGradeIdentificacaoDaSala: this.salas[this.salaSelecionada-1].identificacaoDaSala,
      exameGradeId: cadastroGradeValor.exameGradeId,
      exameGradeNome: this.exames[cadastroGradeValor.exameGradeId-1].nome,
      grupoGradeId: this.grupoSelecionado,
      grupoGradeNome: this.gruposDeExame[this.grupoSelecionado-1].nome,
    };
    
    this.gradeAgendamentoService.cadastrarGrade(this.grade).subscribe((response) => {
      Object.assign(this.grade, response);
      this.gradeSalva.emit(this.grade)
    });
    this.cadastroGrade.reset();
  }

  ngOnInit(): void {
    this.unidadeFuncionalService.GetUnidades().subscribe((response) => {
      this.unidadesExecutoras = response;
    });

    this.servidorService.getServidor().subscribe((response) => {
      this.servidores = response;
    });

    this.gradeAgendamentoService.getSalas().subscribe((response) => {
      this.salas = response;
    });

    this.exameService.GetExames().subscribe((response) => {
      this.exames = response;
    });

    this.grupoExameService.GetGrupos().subscribe((response) => {
      this.gruposDeExame = response;
    });


  }


}
