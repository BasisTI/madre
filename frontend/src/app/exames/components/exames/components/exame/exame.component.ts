import { Component, Input, OnInit, OnChanges, SimpleChanges, Output } from '@angular/core';
import { ExamModel } from 'src/app/exames/models/subjects/exames-model';
import { ExamesService } from '../../../../services/exames.service'

@Component({
  selector: 'app-exame',
  templateUrl: './exame.component.html',
  styleUrls: ['./exame.component.css']
})
export class ExameComponent implements OnInit, OnChanges {

  @Input()
  GrupoID: number;

  exames: ExamModel[] = [];

  @Output()
  examesSelecionados: ExamModel[];

  constructor( private exameService: ExamesService ) { }

  ngOnChanges(changes: SimpleChanges): void {
    this.listarExames();
  }

  ngOnInit(): void {

  }

  // Métodos
  listarExames() {
    this.exameService.GetExamesPorGrupo(this.GrupoID).subscribe((response) => {
      this.exames = response;
      console.log(this.GrupoID)
    })
  }

}
