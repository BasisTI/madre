import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Internacao } from '@internacao/models/internacao';
import { InternacaoDePacienteService } from '@internacao/services/internacao-de-paciente.service';
import { Medicamentos } from 'src/app/farmacia/farmacia/medicamentos/Medicamento';

@Component({
  selector: 'app-formulario-dados-internacao',
  templateUrl: './formulario-dados-internacao.component.html',
  styleUrls: ['./formulario-dados-internacao.component.css']
})
export class FormularioDadosInternacaoComponent implements OnInit {

  public internacao: Internacao = new Internacao;

  constructor(
    private internacaoDePacienteService: InternacaoDePacienteService,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
  }

  load(id) {
    this.internacaoDePacienteService.find(id).subscribe((internacao) => {
      this.internacao = this.internacao;
    })
  }
}
