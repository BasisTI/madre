import { OpcaoCombo } from './opcao-combo';
import { OPCAO_SELECIONE } from './opcao-selecione';

export const OPCOES_DE_ORIGEM: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Nascimento ',
        value: 'NASCIMENTO',
    },
    {
        label: 'Ambulatório',
        value: 'AMBULATORIO',
    },
    {
        label: 'Internação',
        value: 'INTERNACAO',
    },
    {
        label: 'Urgência',
        value: 'URGENCIA',
    },
    {
        label: 'Paciente Externo',
        value: 'PACIENTE_EXTERNO',
    },
    {
        label: 'Doação de sangue',
        value: 'DOACAO_DE_SANGUE',
    },
    {
        label: 'Hospital dia',
        value: 'HOSPITAL_DIA',
    },
    {
        label: 'Cirurgia',
        value: 'CIRURGIA',
    },
    {
        label: 'Todas as origens',
        value: 'TODAS_AS_ORIGENS',
    },
    {
        label: 'Emergência',
        value: 'EMERGENCIA',
    },
];
