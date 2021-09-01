import { Time } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import * as moment from 'moment';
import { MessageService } from 'primeng/api';
import { DiaSemana } from '../../models/dropdowns/dia.dropdown';
import { HorarioAgendado } from '../../models/subjects/horario-agendado';
import { TipoDeMarcacao } from '../../models/subjects/tipo-de-marcacao';
import { GradeDeAgendamentoService } from '../../services/grade-de-agendamento.service';

@Component({
  selector: 'app-formulario-horarios-agendados',
  templateUrl: './formulario-horarios-agendados.component.html',
  styleUrls: ['./formulario-horarios-agendados.component.css']
})
export class FormularioHorariosAgendadosComponent implements OnInit {

  horarioInicio: Date;
  horaFim: Date;
  duracao: Time;
  duracaoPadrao = new Date('December 31, 2021 00:30:00');
  dia = DiaSemana;
  diaSelecionado: string;
  tiposDeMarcacao: TipoDeMarcacao[] = [];
  numeroDeHorarios: number;

  constructor(private gradeService: GradeDeAgendamentoService,
    private fb: FormBuilder, private msg: MessageService) { }

  ngOnInit(): void {
    this.gradeService.getTiposDeMarcacao().subscribe((response) => {
      this.tiposDeMarcacao = response;
    });
  }

  agendarHorario = this.fb.group({
    horaInicio: [null, Validators.required],
    horaFim: [null],
    numeroDeHorarios: [null],
    duracao: [null, Validators.required],
    ativo: [null, Validators.required],
    exclusivo: [null, Validators.required]
  });

  agendarHorarioGrade() {
    const cadastroHorario = this.agendarHorario.value;

    let dataDuracao: Date = cadastroHorario.duracao;

    let valorDuracao: moment.Duration = moment.duration({
      seconds: dataDuracao.getSeconds(),
      minutes: dataDuracao.getMinutes(),
      hours: dataDuracao.getHours()
    });

    const cadastro: HorarioAgendado = {
      horaInicio: cadastroHorario.horaInicio,
      horaFim: cadastroHorario.horaFim,
      numeroDeHorarios: cadastroHorario.numeroDeHorarios,
      dia: this.diaSelecionado,
      duracao: valorDuracao,
      ativo: cadastroHorario.ativo,
      exclusivo: cadastroHorario.exclusivo
    };


    if (this.numeroDeHorarios != null && this.horaFim != null) {
      this.msg.add({
        severity: 'error', summary: 'Erro no preenchimento',
        detail: 'Não informar hora fim e número de horários ao mesmo tempo.'
      })
      return;
    }

    if (moment(this.horarioInicio).isAfter(this.horaFim)) {
      this.msg.add({
        severity: 'error', summary: 'Erro no preenchimento',
        detail: 'Hora fim deve ser depois de hora início.'
      })
      return;
    }

    this.gradeService.cadastrarHorarioGrade(cadastro).subscribe();
    this.agendarHorario.reset();
  }

  validarFormulario(): boolean {
    if (this.agendarHorario.valid && this.diaSelecionado)
      return true;
    else
      return false;
  }


  limparFormulario() {
    this.agendarHorario.reset();
    this.diaSelecionado = null;
  }

}
