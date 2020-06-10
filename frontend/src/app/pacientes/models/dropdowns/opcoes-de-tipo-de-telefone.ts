import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_TIPO_DE_TELEFONE: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Celular',
        value: 'CELULAR',
    },
    {
        label: 'Comercial',
        value: 'COMERCIAL',
    },
    {
        label: 'Emergencial',
        value: 'EMERGENCIAL',
    },
    {
        label: 'Recado',
        value: 'RECADO',
    },
    {
        label: 'Residencial',
        value: 'RESIDENCIAL',
    },
];
