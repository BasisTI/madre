import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tabela-exames',
  templateUrl: './tabela-exames.component.html',
  styleUrls: ['./tabela-exames.component.css']
})
export class TabelaExamesComponent implements OnInit {

  numbers: string[] = ["1","2"];

  constructor() { }

  ngOnInit(): void {
  }

}
