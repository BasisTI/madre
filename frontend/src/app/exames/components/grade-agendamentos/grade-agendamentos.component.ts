import { Component, OnInit } from '@angular/core';
import { GradeDeAgendamentoExame } from '../../models/subjects/grades-de-agendamento';

@Component({
  selector: 'app-grade-agendamentos',
  templateUrl: './grade-agendamentos.component.html',
  styleUrls: ['./grade-agendamentos.component.css']
})
export class GradeAgendamentosComponent implements OnInit {

  private gradeCadastrada: GradeDeAgendamentoExame = {};

  constructor() { }

  ngOnInit(): void {
  }

  public get grade(): GradeDeAgendamentoExame {
    return this.gradeCadastrada;
  }

  onGradeSalva(gradeSalva: GradeDeAgendamentoExame){
    this.gradeCadastrada = gradeSalva;
    console.log(gradeSalva);
  }

}
