import { Component, OnInit, OnDestroy } from '@angular/core';

import { BreadcrumbService } from '../../breadcrumb/breadcrumb.service';

interface Paciente {
  prontuario: string;
  nome: string;
  nomeDaMae: string;
  dataDeNascimento: Date;
  numeroDoCartaoSUS: string;
}

@Component({
  selector: 'app-lista-de-pacientes',
  templateUrl: './lista-de-pacientes.component.html',
})
export class ListaDePacientesComponent implements OnInit, OnDestroy {
  searchUrl = '';
  listaDePacientes: Paciente[] = [
    {
      prontuario: '123456789',
      nomeDaMae: 'Maria da Silva',
      nome: 'João da Silva',
      dataDeNascimento: new Date(),
      numeroDoCartaoSUS: '123456789000000',
    },
    {
      prontuario: '123456789',
      nomeDaMae: 'Maria da Silva',
      nome: 'João da Silva',
      dataDeNascimento: new Date(),
      numeroDoCartaoSUS: '123456789000000',
    },
    {
      prontuario: '123456789',
      nomeDaMae: 'Maria da Silva',
      nome: 'João da Silva',
      dataDeNascimento: new Date(),
      numeroDoCartaoSUS: '123456789000000',
    },
    {
      prontuario: '123456789',
      nomeDaMae: 'Maria da Silva',
      nome: 'João da Silva',
      dataDeNascimento: new Date(),
      numeroDoCartaoSUS: '123456789000000',
    },
  ];

  constructor(private breadcrumbService: BreadcrumbService) {}

  ngOnInit(): void {
    this.breadcrumbService.setItems([{ label: 'Pacientes' }]);
  }

  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }
}
