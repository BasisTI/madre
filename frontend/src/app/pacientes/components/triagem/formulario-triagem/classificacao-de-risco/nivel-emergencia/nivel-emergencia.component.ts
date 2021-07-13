import { Component, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CLASSIFICACAO_RISCO } from 'src/app/pacientes/models/radioButton/classificacao-risco';

@Component({
  selector: 'app-nivel-emergencia',
  templateUrl: './nivel-emergencia.component.html',
  styleUrls: ['./nivel-emergencia.component.css']
})
export class NivelEmergenciaComponent implements OnInit {

  @Input() formTriagem: FormGroup;
  opcaoClassificacao = CLASSIFICACAO_RISCO;

  @Input()
  title: string;

  @Input()
  descricao: string;

  @Input()
  cor: string;

  @Input()
  nivel: number;

  @Input()
  descricaoC: string;

  constructor() { }

  ngOnInit(): void {
  }
}
