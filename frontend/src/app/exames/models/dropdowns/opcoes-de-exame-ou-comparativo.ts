import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_EXAME_OU_COMPARATIVO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: '1º Exame',
        value: 'PRIMEIRO_EXAME',
    },
    {
        label: 'Comparativo',
        value: 'COMPARATIVO',
    },
];
