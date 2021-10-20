import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Sinonimos } from '../../models/subjects/sinonimos';
import { SinonimosExamesComponent } from '../sinonimos-exames/sinonimos-exames.component';

@Component({
    selector: 'app-exames-form',
    templateUrl: './exames-form.component.html',
    styleUrls: ['./exames-form.component.css'],
})
export class ExamesFormComponent implements OnInit, AfterViewInit {

  @ViewChild(SinonimosExamesComponent, {static: false}) appSinonimo: SinonimosExamesComponent;

  sinonimos: Sinonimos[] = [];

  ngAfterViewInit(){
    this.sinonimos = this.appSinonimo.sinonimos;
  }

  constructor() { }

  ngOnInit(): void {
  }

  opa() {
    console.log(this.sinonimos);
  }

}
