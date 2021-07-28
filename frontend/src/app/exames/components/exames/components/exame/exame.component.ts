import { Component, Input, OnInit, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
// import { Observable, Subject } from 'rxjs';
import { ExamModel } from 'src/app/exames/models/subjects/exames-model';
import { ExamesService } from '../../../../services/exames.service'

@Component({
  selector: 'app-exame',
  templateUrl: './exame.component.html',
  styleUrls: ['./exame.component.css']
})
export class ExameComponent implements OnInit, OnChanges {

  @Input()
  grupoID: number;

  @Output()
  aoSelecionar = new EventEmitter<ExamModel[]>();

  // Constantes
  exames: ExamModel[] = [];
  examesSelecionados: ExamModel[] = [];

  constructor( private exameService: ExamesService ) { }

  ngOnChanges(changes: SimpleChanges): void {
    this.listarExames();
  }

  ngOnInit(): void { }

  // Métodos
  listarExames() {
    this.exameService.GetExamesPorGrupo(this.grupoID).subscribe((response) => {
      this.exames = response;
    })
  }

  AdicionarExame() {
    this.aoSelecionar.emit(this.examesSelecionados)
  }

  visualizar() {
    console.log("teste");
  }
  
}
