import { Component, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CLASSIFICACAO_RISCO } from 'src/app/pacientes/models/radioButton/classificacao-risco';

@Component({
  selector: 'app-nivel-emergencia',
  templateUrl: './nivel-emergencia.component.html',
  styleUrls: ['./nivel-emergencia.component.css']
})
export class NivelEmergenciaComponent implements OnInit {

  @Input() control: FormControl;

  @Input()
  title: string;

  @Input()
  descricao: string;

  @Input()
  cor: string;

  @Input()
  value: string;

  @Input()
  nivel: number;

  @Input()
  descricaoC: string;

  constructor() { }

  ngOnInit(): void {
  }
}
