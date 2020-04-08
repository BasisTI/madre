import { Component } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';

import * as moment from 'moment';

import { ptBR } from '../../../shared/calendar.pt-br.locale';

@Component({
  selector: 'app-dados-pessoais',
  templateUrl: './dados-pessoais.component.html',
  styles: [
    `
      div {
        margin: 3px;
      }

      input:disabled {
        color: black;
        opacity: unset;
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
    nacionalidade: [''],
    naturalidade: [''],
    grauDeInstrucao: [''],
    ocupacao: [''],
    religiao: [''],
    email: [''],
  });

  idade = '';
  uf = '';

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
      value: { value: 'BRASILIA', uf: 'DF' },
    },
    {
      label: 'Goiás',
      value: { value: 'GOIAS', uf: 'GO' },
    },
    {
      label: 'Curitiba',
      value: { value: 'CURITIBA', uf: 'PR' },
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
  dataLimite = new Date();
  anosDisponiveis = `1900:${this.dataLimite.getFullYear()}`;
  formatoDeData = 'dd/mm/yy';

  constructor(private fb: FormBuilder) {}

  aoSelecionarDataDeNascimento() {
    const { dataDeNascimento } = this.dadosPessoais.value;

    if (dataDeNascimento) {
      this.idade = String(moment().diff(moment(dataDeNascimento), 'years'));
      return;
    }

    this.idade = '';
  }

  aoSelecionarNaturalidade() {
    const { naturalidade } = this.dadosPessoais.value;

    if (naturalidade) {
      if (naturalidade.uf) {
        this.uf = naturalidade.uf;
        return;
      }
    }

    this.uf = '';
  }
}
