import { Component } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';

import { ptBR } from '../../../shared/calendar.pt-br.locale';

@Component({
  selector: 'app-dados-pessoais',
  templateUrl: './dados-pessoais.component.html',
  styles: [
    `
      .ui-widget.read-only:disabled {
        opacity: 1;
        background-color: #dddddd;
      }

      div {
        margin: 3px;
      }
    `,
  ],
})
export class DadosPessoaisComponent {
  dadosPessoais: FormGroup = this.fb.group({
    nome: [''],
    nomeSocial: [''],
    sexo: [''],
    raca: [''],
    etnia: [''],
    estadoCivil: [''],
    prontuarioMae: [''],
    nomeDaMae: [''],
    nomeDoPai: [''],
    dataDeNascimento: [''],
    horaDoNascimento: [''],
    idade: [''],
    nacionalidade: [''],
    naturalidade: [''],
    uf: [''],
    grauDeInstrucao: [''],
    ocupacao: [''],
    religiao: [''],
    email: [''],
  });

  opcoesDeSexo = [
    {
      label: 'Selecione',
    },
    {
      label: 'Masculino',
      value: 'MASCULINO',
    },
    {
      label: 'Feminino',
      value: 'FEMININO',
    },
  ];

  opcoesDeRaca = [
    {
      label: 'Selecione',
    },
    {
      label: 'Branca',
      value: 'BRANCA',
    },
    {
      label: 'Preta',
      value: 'PRETA',
    },
  ];

  opcoesDeEtnia = [
    {
      label: 'Selecione',
    },
    {
      label: 'Etnia 1',
      value: 'ETNIA_1',
    },
    {
      label: 'Etnia 2',
      value: 'ETNIA_2',
    },
  ];

  opcoesDeEstadoCivil = [
    {
      label: 'Selecione',
    },
    {
      label: 'Solteiro',
      value: 'SOLTEIRO',
    },

    {
      label: 'Casado',
      value: 'CASADO',
    },
  ];

  opcoesDeNacionalidade = [
    {
      label: 'Selecione',
    },
    {
      label: 'Brasileira',
      value: 'BRASILEIRA',
    },
    {
      label: 'Espanhola',
      value: 'ESPANHOLA',
    },
    {
      label: 'Portuguesa',
      value: 'PORTUGUESA',
    },
  ];

  opcoesDeNaturalidade = [
    {
      label: 'Selecione',
    },
    {
      label: 'Brasília',
      value: 'BRASILIA',
    },
    {
      label: 'Goiás',
      value: 'GOIAS',
    },
    {
      label: 'Curitiba',
      value: 'CURITIBA',
    },
  ];

  opcoesDeUF = [
    {
      label: 'Selecione',
    },
    {
      label: 'DF',
      value: 'DF',
    },
    {
      label: 'GO',
      value: 'GO',
    },
    {
      label: 'BA',
      value: 'BA',
    },
  ];

  opcoesDeGrauDeInstrucao = [
    {
      label: 'Selecione',
    },
    {
      label: 'Superior Incompleto',
      value: 'SUPERIOR_INCOMPLETO',
    },
    {
      label: 'Superior Completo',
      value: 'SUPERIOR_COMPLETO',
    },
  ];

  opcoesDeOcupacao = [
    {
      label: 'Selecione',
    },
    {
      label: 'Programador',
      value: 'PROGRAMADOR',
    },
    {
      label: 'Gerente de Projetos',
      value: 'GERENTE_DE_PROJETOS',
    },
  ];

  opcoesDeReligiao = [
    {
      label: 'Selecione',
    },
    {
      label: 'Católico',
      value: 'CATOLICO',
    },
    {
      label: 'Evangélico',
      value: 'EVANGELICO',
    },
  ];

  localizacao = ptBR;
  maxDate = new Date();
  yearRange = `1900:${this.maxDate.getFullYear()}`;

  constructor(private fb: FormBuilder) {}

  onSubmit() {
    console.log(this.dadosPessoais.value);
  }
}
