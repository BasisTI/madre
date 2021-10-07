import { Component, Input, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';
import { GradeDeAgendamentoService } from '../../services/grade-de-agendamento.service';
import { ExamModel } from '../../models/subjects/exames-model';
import { GradeDeAgendamentoExame } from '../../models/subjects/grades-de-agendamento';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { Sala } from '../../models/subjects/sala';
import { ExamesService } from '../../services/exames.service';
import { ListaServidor } from 'src/app/seguranca/models/dropdowns/lista-servidor';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { UnidadeFuncionalComponent } from '@shared/components/unidade-funcional/unidade-funcional.component';
import { HorarioExame } from '../../models/subjects/horario-agendado';
import { Dia } from '../../models/subjects/dia';
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

  dias: Array<Dia>;
  diasSelecionados: Array<Dia>;
  horariosDaGrade: Array<HorarioExame>;
  unidadeSelecionada: number;

  listaUnidades: UnidadeFuncional[] = [];
  listaServidores: ListaServidor[] = [];
  listaSalas: Sala[] = [];
  listaExames: ExamModel[] = [];

  situacaoGrade = SituacaoAtivo;

  gradeId: number;

  @Input()
  grade: GradeDeAgendamentoExame;

  @Output()
  gradeSalva = new EventEmitter<GradeDeAgendamentoExame>();

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
    if (this.cadastroGrade.valid && this.horaFim && this.horaInicio && this.dataFim && this.dataInicio && this
      .unidadeSelecionada && this.diasSelecionados) {
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
    this.diasSelecionados = null;
  }

  confirmarGravacaoDaGrade() {
    this.confirmacaoService.confirm({
      message: 'Gostaria de agendar horários para esta grade agora?',
      header: 'Salvar grade',
      icon: 'pi pi-question',
      accept: () => {
        this.msg.add({
          severity: 'info',
          detail: "Acesse 'Horários' para marcar horários nessa grade."
        });
      },
      reject: () => {
        this.router.navigate(['/listar-grade-exame']);
      }
    });
  }

  cadastrarGradeDeAgendamento() {
    const cadastroGradeValor = this.cadastroGrade.value;

    this.grade = {
      dataInicio: this.dataInicio,
      dataFim: this.dataFim,
      horaInicio: this.gerarHora(this.horaInicio, this.dataInicio),
      horaFim: this.gerarHora(this.horaFim, this.dataFim),
      dias: this.diasSelecionados,
      numeroDeHorarios: cadastroGradeValor.numeroDeHorarios,
      ativo: cadastroGradeValor.ativo,
      unidadeExecutoraId: this.unidadeSelecionada,
      responsavelId: cadastroGradeValor.responsavelId,
      exameId: cadastroGradeValor.exameId,
      salaId: cadastroGradeValor.salaId,
      duracao: this.gerarDuracao(30),
    };

    if (this.isInicioDepoisDeFim(this.horaInicio, this.horaFim)) {
      return;
    } else {
      this.gradeAgendamentoService.cadastrarGrade(this.grade).subscribe((response) => {
        Object.assign(this.grade, response);
        this.gradeSalva.emit(this.grade);
      });
      this.limparFormulario();
      this.confirmarGravacaoDaGrade();
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

  gerarHora(hora: Date, data: Date): Date {
    let horaGerada = new Date(Date.UTC(data.getFullYear(), data.getMonth(), data.getDate(), hora.getHours(), 
    hora.getMinutes()));

    return horaGerada;
  }

  gerarDuracao(minutos: number): moment.Duration {
    let valorDuracao = moment.duration({
      minutes: minutos
    });
    return valorDuracao;
  }

  listarUnidades() {
    this.unidadeFuncionalService.getUnidades().subscribe((response) => {
      this.listaUnidades = response;
    });
  }

  listarServidores() {
    this.servidorService.getServidor().subscribe((response) => {
      this.listaServidores = response;
    });
  }

  listarSalas() {
    this.gradeAgendamentoService.getSalasPorUnidade(this.unidadeSelecionada.toString(), 'true').subscribe((response) => {
      this.listaSalas = response;
    });
  }

  listarExames() {
    this.exameService.getExames().subscribe((response) => {
      this.listaExames = response;
    });
  }

  listarDias() {
    this.gradeAgendamentoService.getDias().subscribe((response) => {
      this.dias = response;
    });
  }

  ngOnInit(): void {
    this.listarDias();
    console.log('Dias: ', this.dias);
    

    this.listarUnidades();

    this.listarServidores();

    this.listarExames();
  }


}
