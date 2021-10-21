import { DarAltaAoPaciente } from './../../models/dar-alta-ao-paciente';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dar-alta-ao-paciente',
  templateUrl: './dar-alta-ao-paciente.component.html',
  styleUrls: ['./dar-alta-ao-paciente.component.css']
})
export class DarAltaAoPacienteComponent implements OnInit {

    private altaCadastrada: DarAltaAoPaciente = {}

  constructor() { }

  ngOnInit(): void {
  }

}
