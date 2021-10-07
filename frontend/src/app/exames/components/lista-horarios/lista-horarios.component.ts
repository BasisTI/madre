import { Component, Input, OnInit, Output } from '@angular/core';
import { GradeDeAgendamentoExame } from '../../models/subjects/grades-de-agendamento';
import { HorarioExame } from '../../models/subjects/horario-agendado';
import { GradeDeAgendamentoService } from '../../services/grade-de-agendamento.service';

@Component({
  selector: 'app-lista-horarios',
  templateUrl: './lista-horarios.component.html',
  styleUrls: ['./lista-horarios.component.css']
})
export class ListaHorariosComponent implements OnInit {

  @Input()
  grade: GradeDeAgendamentoExame;
  
  id: string = '';
  horaInicio: string = '';
  horaFim: string = '';
  numeroDeHorarios: string = '';
  dia: string = '';
  duracao: string = '';
  ativo: string = '';
  exclusivo: string = '';


  horariosAgendados: HorarioExame[];

  constructor(private gradeAgendamentoService: GradeDeAgendamentoService) { }

  
  listarHorariosAgendados() {
    this.gradeAgendamentoService.getHorariosExame(this.grade.id.toString()).subscribe((response) => {
      this.horariosAgendados = response;
    });
  }
  
  ngOnInit(): void {
    this.listarHorariosAgendados();
  }
}
