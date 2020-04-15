import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_GRAU_DE_INSTRUCAO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Nenhum',
        value: 'NENHUM',
    },
    {
        label: '1º Grau Completo',
        value: 'PRIMEIRO_GRAU_COMPLETO',
    },
    {
        label: '1º Grau Incompleto',
        value: 'PRIMEIRO_GRAU_INCOMPLETO',
    },
    {
        label: '2º Grau Completo',
        value: 'SEGUNDO_GRAU_COMPLETO',
    },
    {
        label: '2º Grau Incompleto',
        value: 'SEGUNDO_GRAU_INCOMPLETO',
    },
    {
        label: 'Superior Incompleto',
        value: 'SUPERIOR_INCOMPLETO',
    },
    {
        label: 'Superior Completo',
        value: 'SUPERIOR_COMPLETO',
    },
    {
        label: 'Ignorado',
        value: 'IGNORADO',
    },
];
