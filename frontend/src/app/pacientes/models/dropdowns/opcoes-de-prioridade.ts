import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_PRIORIDADE: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Eletiva',
        value: 'ELETIVA',
    },
    {
        label: 'Urgencia',
        value: 'URGENCIA',
    },
];
