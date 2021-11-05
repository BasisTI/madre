import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
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
    this.exameService.getExamesPorGrupo(this.grupoID).subscribe((response) => {
      this.exames = response;
    })
  }

  pegarExames(): ExamModel[] {
    return this.examesSelecionados;
  }
  
}
