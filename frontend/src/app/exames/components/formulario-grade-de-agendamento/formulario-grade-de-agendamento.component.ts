import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';
import { GradeDeAgendamentoService } from '../../services/gradeDeAgendamento.service';
import { GrupoModel } from '../../models/subjects/grupo-model';
import { ExamModel } from '../../models/subjects/exames-model';
import { Responsavel } from '../../models/subjects/responsavel-model';
import { GradesDeAgendamento } from '../../models/subjects/grades-de-agendamento';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { Servidor } from 'src/app/seguranca/models/dropdowns/servidor-model';
import { PessoaService } from 'src/app/seguranca/services/pessoa.service';
import { Pessoa } from 'src/app/seguranca/models/dropdowns/pessoa-model';
import { Sala } from '../../models/subjects/sala';
import { ExamesService } from '../../services/exames.service';
import { GruposExamesService } from '../../services/grupos-exames.service';

@Component({
  selector: 'app-formulario-grade-de-agendamento',
  templateUrl: './formulario-grade-de-agendamento.component.html',
  styleUrls: ['./formulario-grade-de-agendamento.component.css']
})
export class FormularioGradeDeAgendamentoComponent implements OnInit {

 

  constructor(private fb: FormBuilder,
              private gradeAgendamentoService: GradeDeAgendamentoService,
              private unidadeFuncionalService: UnidadeFuncionalService,
              private pessoaService: PessoaService,
              private exameService: ExamesService,
              private grupoExameService: GruposExamesService) { }
  

  unidadesExecutoras: UnidadeFuncional[] = [];
  pessoas: Pessoa[] = [];
  salas: Sala[] = [];
  gruposDeExame: GrupoModel[] = [];
  exames: ExamModel[] = [];
  responsaveis: Responsavel[] = [];

  situacaoGrade = SituacaoAtivo;
  

  cadastroGrade = this.fb.group({
    grade: [null, Validators.required],
    unidadeExecutoraId: [null, Validators.required],
    responsavelId: [null, Validators.required],
    ativo: [null, Validators.required],
    salaId: [null, Validators.required],
    exameId: [null],
    grupoAgendamentoExameId: [null]
  });

  validarFormulario(): boolean {
    if (this.cadastroGrade.valid && (this.cadastroGrade
        .get('exameId').value != null || this.cadastroGrade
        .get('grupoAgendamentoExameId').value != null)){
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

    const cadastro: GradesDeAgendamento = {
      grade: cadastroGradeValor.grade,
      unidadeExecutoraId: cadastroGradeValor.unidadeExecutoraId,
      responsavelId: cadastroGradeValor.responsavelId,
      ativo: cadastroGradeValor.ativo,
      salaId: cadastroGradeValor.salaId,
      exameId: cadastroGradeValor.exameId,
      grupoAgendamentoExameId: cadastroGradeValor.grupoAgendamentoExameId
    };

    console.log(cadastroGradeValor);

    this.gradeAgendamentoService.cadastrarGrade(cadastro).subscribe();
    this.cadastroGrade.reset();
    
  }

  ngOnInit(): void {
    this.unidadeFuncionalService.GetUnidades().subscribe((response) => {
      this.unidadesExecutoras = response;
    });

    this.pessoaService.getPessoa().subscribe((response) => {
      this.pessoas = response;
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
