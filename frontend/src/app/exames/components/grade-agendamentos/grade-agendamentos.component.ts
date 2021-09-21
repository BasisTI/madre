import { Component, OnInit } from '@angular/core';
import { GradesDeAgendamento } from '../../models/subjects/grades-de-agendamento';

@Component({
  selector: 'app-grade-agendamentos',
  templateUrl: './grade-agendamentos.component.html',
  styleUrls: ['./grade-agendamentos.component.css']
})
export class GradeAgendamentosComponent implements OnInit {

  private gradeCadastrada: GradesDeAgendamento = {};

  constructor() { }

  ngOnInit(): void {
  }

  public get grade(): GradesDeAgendamento {
    return this.gradeCadastrada;
  }

  onGradeSalva(gradeSalva: GradesDeAgendamento){
    this.gradeCadastrada = gradeSalva;
    console.log(gradeSalva);
  }

}
