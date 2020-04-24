import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_SEXO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Masculino',
        value: 'MASCULINO',
    },
    {
        label: 'Feminino',
        value: 'FEMININO',
    },
    {
        label: 'Ignorado',
        value: 'IGNORADO',
    },
];
