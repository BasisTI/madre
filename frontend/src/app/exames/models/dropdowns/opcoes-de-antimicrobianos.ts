import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_ANTIMICROBIANOS: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Sim',
        value: 'SIM',
    },
    {
        label: 'Não',
        value: 'NAO',
    },
];
