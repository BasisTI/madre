import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_TIPO_DE_ENDERECO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Contato',
        value: 'CONTATO',
    },
    {
        label: 'Comercial',
        value: 'COMERCIAL',
    },
    {
        label: 'Residencial',
        value: 'RESIDENCIAL',
    },
    {
        label: 'Outros',
        value: 'OUTROS',
    },
];
