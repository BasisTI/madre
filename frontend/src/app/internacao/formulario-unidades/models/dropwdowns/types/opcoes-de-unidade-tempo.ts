import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';
import { OpcaoCombo } from './../../../../../pacientes/models/dropdowns/opcao-combo';

export const OPCOES_DE_UNIDADE_TEMPO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Horas',
        value: 'HORAS',
    },
    {
        label: 'Dia',
        value: 'DIA',
    },
];
