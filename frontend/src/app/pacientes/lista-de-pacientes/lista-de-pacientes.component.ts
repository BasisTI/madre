import { Component } from '@angular/core';

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
export class ListaDePacientesComponent {
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
}
