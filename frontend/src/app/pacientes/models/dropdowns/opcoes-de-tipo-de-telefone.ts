import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_TIPO_DE_TELEFONE: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Celular',
        value: 'CELULAR',
    },
    {
        label: 'Residencial',
        value: 'RESIDENCIAL',
    },
    {
        label: 'Recado',
        value: 'RECADO',
    },
    {
        label: 'Comercial',
        value: 'COMERCIAL',
    },
    {
        label: 'Emergencial',
        value: 'EMERGENCIAL',
    },
];
