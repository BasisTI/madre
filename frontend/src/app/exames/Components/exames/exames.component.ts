import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit {

  selectedValue: String;
  value: Boolean;

  constructor() { }


  ngOnInit(): void {
  }

}
