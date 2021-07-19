import { Component, Input, OnInit } from '@angular/core';
import { ExamModel } from 'src/app/exames/models/subjects/exames-model';
import { ExamesService } from '../../../../services/exames.service'

@Component({
  selector: 'app-exame',
  templateUrl: './exame.component.html',
  styleUrls: ['./exame.component.css']
})
export class ExameComponent implements OnInit {

  @Input()
  Exames: ExamModel[];

  teste: boolean;
  exames: ExamModel[] = [];
  examesSelecionados: ExamModel[];

  constructor( private exameService: ExamesService ) { }

  ngOnInit(): void {

    this.exameService.GetExames().subscribe((response) => {
      this.exames = response;
      console.log(response, "uswhsiu")
    })
  }

}
