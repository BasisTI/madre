import { Component, Input, OnInit } from '@angular/core';
import { ExamModel } from 'src/app/exames/models/subjects/exames-model';
import { GrupoModel } from 'src/app/exames/models/subjects/grupo-model';

@Component({
  selector: 'app-exame',
  templateUrl: './exame.component.html',
  styleUrls: ['./exame.component.css']
})
export class ExameComponent implements OnInit {

  @Input()
  Exames: ExamModel[];

  constructor() { }

  ngOnInit(): void {
  }

}
