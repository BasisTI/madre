import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_TIPO_DE_TELEFONE: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Contato',
        value: 'CONTATO',
    },
    {
        label: 'Residencial',
        value: 'RESIDENCIAL',
    },
    {
        label: 'Comercial',
        value: 'COMERCIAL',
    },
    {
        label: 'Outros',
        value: 'OUTROS',
    },
];
