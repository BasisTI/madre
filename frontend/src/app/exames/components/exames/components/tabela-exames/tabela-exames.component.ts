import { Component, Input, OnInit } from '@angular/core';
import { ExamModel } from 'src/app/exames/models/subjects/exames-model';

@Component({
  selector: 'app-tabela-exames',
  templateUrl: './tabela-exames.component.html',
  styleUrls: ['./tabela-exames.component.css']
})
export class TabelaExamesComponent implements OnInit {

  
  @Input()
  examesAdicionados: ExamModel;

  constructor() { }

  ngOnInit(): void {
  }

}
