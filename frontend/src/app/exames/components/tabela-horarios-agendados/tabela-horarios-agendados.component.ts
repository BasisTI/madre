import { Component, OnInit } from '@angular/core';
import { HorarioAgendado } from '../../models/subjects/horario-agendado';
import { GradeDeAgendamentoService } from '../../services/grade-de-agendamento.service';

@Component({
  selector: 'app-tabela-horarios-agendados',
  templateUrl: './tabela-horarios-agendados.component.html',
  styleUrls: ['./tabela-horarios-agendados.component.css']
})
export class TabelaHorariosAgendadosComponent implements OnInit {

  id: string = '';
  horaInicio: string = '';
  horaFim: string = '';
  numeroDeHorarios: string = '';
  dia: string = '';
  duracao: string = '';
  ativo: string = '';
  exclusivo: string = '';

  horariosAgendados: HorarioAgendado[];

  constructor(private gradeAgendamentoService: GradeDeAgendamentoService) { }

  ngOnInit(): void {
    this.listarHorariosAgendados();
  }

  listarHorariosAgendados() {
    this.gradeAgendamentoService.getHorariosAgendados().subscribe((response) => {
      this.horariosAgendados = response;
    });
  }

}
