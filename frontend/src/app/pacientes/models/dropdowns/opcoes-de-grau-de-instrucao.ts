import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_GRAU_DE_INSTRUCAO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Superior Incompleto',
        value: 'SUPERIOR_INCOMPLETO',
    },
    {
        label: 'Superior Completo',
        value: 'SUPERIOR_COMPLETO',
    },
];
