import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_TIPO_DE_TELEFONE: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Residencial',
        value: 'Residencial',
    },
    {
        label: 'Comercial',
        value: 'Comercial',
    },
    {
        label: 'Outros',
        value: 'Outros',
    },
];
