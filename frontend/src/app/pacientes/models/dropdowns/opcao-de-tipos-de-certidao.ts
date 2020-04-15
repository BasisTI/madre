import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_TIPO_DE_CERTIDAO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Nascimento',
        value: 'nascimento',
    },
    {
        label: 'Casamento',
        value: 'casamento',
    },
    {
        label: 'Separacao/Divorcio',
        value: 'separacaoDivorcio',
    },
    {
        label: 'Indigena',
        value: 'indigena',
    },
];
