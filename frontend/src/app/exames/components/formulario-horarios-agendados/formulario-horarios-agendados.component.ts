import { Time } from '@angular/common';
import { THIS_EXPR, ThrowStmt } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
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
  horarioInicio: Time;
  horarioFim: Time;
  duracao: Time;
  dia = DiaSemana;
  tiposDeMarcacao: TipoDeMarcacao[] = [];

  diaSelecionado: string;

  constructor(private gradeService: GradeDeAgendamentoService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    console.log(new Date);
    
    this.gradeService.getTiposDeMarcacao().subscribe((response) => {
      this.tiposDeMarcacao = response;
    });
  }

  agendarHorario = this.fb.group({
    horaInicio: [null, Validators.required],
    horaFim: [null],
    numeroDeHorarios: [null],
    // dia: [null, Validators.required],
    duracao: [null, Validators.required],
    ativo: [null, Validators.required],
    exclusivo: [null, Validators.required]
  });

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

  agendarHorarioGrade() {
    const cadastroHorario = this.agendarHorario.value;

    const cadastro: HorarioAgendado = {
      horaInicio: cadastroHorario.horaInicio,
      horaFim: cadastroHorario.horaFim,
      numeroDeHorarios: cadastroHorario.numeroDeHorarios,
      dia: this.diaSelecionado,
      duracao: cadastroHorario.duracao,
      ativo: cadastroHorario.ativo,
      exclusivo: cadastroHorario.exclusivo
    };

    console.log(cadastroHorario);

    this.gradeService.cadastrarHorarioGrade(cadastro).subscribe();
    this.agendarHorario.reset();
  }


}
