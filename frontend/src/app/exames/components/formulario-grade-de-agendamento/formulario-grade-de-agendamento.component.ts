import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';
import { GradeDeAgendamentoService } from '../../services/grade-de-agendamento.service';
import { ExamModel } from '../../models/subjects/exames-model';
import { GradeDeAgendamentoExame } from '../../models/subjects/grades-de-agendamento';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { Sala } from '../../models/subjects/sala';
import { DiaSemana } from '../../models/dropdowns/dia.dropdown';
import { ExamesService } from '../../services/exames.service';
import { ListaServidor } from 'src/app/seguranca/models/dropdowns/lista-servidor';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { UnidadeFuncionalComponent } from '@shared/components/unidade-funcional/unidade-funcional.component';
import { HorarioAgendado } from '../../models/subjects/horario-agendado';
@Component({
  selector: 'app-formulario-grade-de-agendamento',
  templateUrl: './formulario-grade-de-agendamento.component.html',
  styleUrls: ['./formulario-grade-de-agendamento.component.css']
})
export class FormularioGradeDeAgendamentoComponent implements OnInit {

  dataInicio: Date;
  dataFim: Date;

  horaInicio: Date;
  horaFim: Date;

  dataMinima: Date = new Date();
  horaPadrao = new Date('December 31, 2020 12:00:00');

  opcoesDia = DiaSemana;
  dia: string;

  unidadeSelecionada: number;

  horariosDaGrade: Array<HorarioAgendado>;


  unidadesExecutoras: UnidadeFuncional[] = [];
  servidores: ListaServidor[] = [];
  salas: Sala[] = [];
  exames: ExamModel[] = [];

  situacaoGrade = SituacaoAtivo;


  constructor(private fb: FormBuilder,
    private gradeAgendamentoService: GradeDeAgendamentoService,
    private unidadeFuncionalService: UnidadeFuncionalService,
    private servidorService: ServidorService,
    private exameService: ExamesService,
    private confirmacaoService: ConfirmationService,
    private router: Router,
    private msg: MessageService) { }

  
    @ViewChild(UnidadeFuncionalComponent)
    unidadeFuncional: UnidadeFuncionalComponent;

  cadastroGrade = this.fb.group({
    numeroDeHorarios: [null, Validators.required],
    ativo: [null, Validators.required],
    responsavelId: [null, Validators.required],
    exameId: [null, Validators.required],
    salaId: [null, Validators.required],
  });

  validarFormulario(): boolean {
    if (this.cadastroGrade.valid) {
      return true;
    }
  }

  limparFormulario() {
    this.cadastroGrade.reset();
    this.horaFim = null;
    this.horaInicio = null;
    this.dataFim = null;
    this.dataInicio = null;
    this.unidadeSelecionada = null;
  }

  confirmarGravacaoDaGrade() {
    this.confirmacaoService.confirm({
      message: 'Gostaria de agendar horários para esta grade agora?',
      header: 'Salvar grade',
      icon: 'pi pi-question',
      accept: () => {
        this.msg.add({
          severity: 'info',
          detail: "Acesse 'Horários Agendados' para marcar horários nessa grade."
        });
      },
      reject: () => {
        this.router.navigate(['/listar-grade-exame']);
      }
    });
  }

  cadastrarGradeDeAgendamento() {
    const cadastroGradeValor = this.cadastroGrade.value;

    const gradeExame: GradeDeAgendamentoExame = {
      dataInicio: this.dataInicio,
      dataFim: this.dataFim,
      horaInicio: this.horaInicio,
      horaFim: this.horaFim,
      dia: this.dia,
      numeroDeHorarios: cadastroGradeValor.numeroDeHorarios,
      ativo: cadastroGradeValor.ativo,
      unidadeExecutoraId: this.unidadeSelecionada,
      responsavelId: cadastroGradeValor.responsavelId,
      exameId: cadastroGradeValor.exameId,
      salaId: cadastroGradeValor.salaId,
      duracao: this.gerarDuracao(30)
    };

    if (this.isInicioDepoisDeFim(this.horaInicio, this.horaFim)) {
      return;
    } else {
      this.gradeAgendamentoService.cadastrarGrade(gradeExame).subscribe();
      this.cadastroGrade.reset();
    }
  }

  isInicioDepoisDeFim(inicio: Date, fim: Date): boolean {
    if (moment(inicio).isAfter(fim)) {
      this.msg.add({
        severity: 'error', summary: 'Erro no preenchimento',
        detail: 'Hora fim deve ser depois de hora início.'
      });
      return true;
    }
  }

  gerarDuracao(minutos: number): moment.Duration {
    let valorDuracao = moment.duration({
      minutes: minutos
    });
    return valorDuracao;
  }

  listarUnidades() {
    this.unidadeFuncionalService.getUnidades().subscribe((response) => {
      this.unidadesExecutoras = response;
    });
  }

  listarServidores() {
    this.servidorService.getServidor().subscribe((response) => {
      this.servidores = response;
    });
  }

  listarSalas() {
    this.gradeAgendamentoService.getSalasPorUnidade(this.unidadeSelecionada.toString(), 'true').subscribe((response) => {
      this.salas = response;
    });
  }

  listarExames() {
    this.exameService.GetExames().subscribe((response) => {
      this.exames = response;
    });

  }

  ngOnInit(): void {
    this.listarUnidades();

    this.listarServidores();

    this.listarExames();
  }


}
