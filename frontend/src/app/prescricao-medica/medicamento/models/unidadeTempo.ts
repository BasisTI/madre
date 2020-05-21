import { OPCAO_SELECIONE } from './../../../shared/dropdown/opcao.selecione';
import { OpcaoCombo } from './../../../pacientes/models/dropdowns/opcao-combo';

export const TIPO_UNIDADE_TEMPO: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Minutos',
        value: 'MINUTOS'
    },

    {
        label: 'Horas',
        value: 'HORAS'
    }
];
