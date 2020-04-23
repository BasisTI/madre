import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_TIPO_DE_CERTIDAO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Casamento',
        value: 'CASAMENTO',
    },
    {
        label: 'Indigena',
        value: 'INDIGENA',
    },
    {
        label: 'Nascimento',
        value: 'NASCIMENTO',
    },

    {
        label: 'Separação / Divórcio',
        value: 'SEPARACAO_DIVORCIO',
    },
];
